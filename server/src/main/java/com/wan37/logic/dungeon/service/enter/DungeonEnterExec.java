package com.wan37.logic.dungeon.service.enter;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.config.DungeonCfgLoader;
import com.wan37.logic.dungeon.init.DungeonSceneCreator;
import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.base.SceneTypeEnum;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.scene.SceneFacade;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.ITeam;
import com.wan37.logic.team.entity.ITeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DungeonEnterExec {

    @Autowired
    private DungeonCfgLoader dungeonCfgLoader;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private DungeonSceneCreator dungeonSceneCreator;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player, Integer dungeonId) {
        SceneCfg sceneCfg = sceneCfgLoader.load(player.getSceneId())
                .orElseThrow(() -> new GeneralErrorExecption("找不到当前场景配置"));

        if (!Objects.equals(sceneCfg.getType(), SceneTypeEnum.SCENE_TYPE_1.getId())) {
            throw new GeneralErrorExecption("请切换到普通场景再尝试进入副本");
        }

        DungeonCfg dungeonCfg = dungeonCfgLoader.load(dungeonId)
                .orElseThrow(() -> new GeneralErrorExecption("找不到相应的副本"));

        SceneCfg dungeonSceneCfg = sceneCfgLoader.load(dungeonCfg.getSceneId())
                .orElseThrow(() -> new GeneralErrorExecption("找不到副本场景配置"));

        if (player.getTeamUid() == null) {
            throw new GeneralErrorExecption("挑战副本需要创建队伍");
        }

        ITeam team = teamGlobalManager.getTeam(player.getTeamUid());
        if (!Objects.equals(team.getLeaderUid(), player.getUid())) {
            throw new GeneralErrorExecption("只有队长才能发起副本挑战");
        }

        if (!isAllOnline(team)) {
            throw new GeneralErrorExecption("有队员不在线");
        }

        List<Player> teamMembers = getTeamMembers(team);
        if (!isInSameScene(teamMembers)) {
            throw new GeneralErrorExecption("队员不在同一场景");
        }

        // 创建副本场景
        DungeonScene dungeonScene = dungeonSceneCreator.create(dungeonCfg, dungeonSceneCfg);
        temporarySceneGlobalManager.addScene(dungeonScene);

        teamMembers.forEach(p -> enterDungeon(p, dungeonScene));
    }

    private void enterDungeon(Player player, DungeonScene dungeonScene) {
        // 离开普通场景
        sceneFacade.leaveScene(player);

        String msg = String.format("[%s副本]挑战开始\n", dungeonScene.getDungeonCfg().getName());
        player.syncClient(msg);

        // 进入副本场景
        temporarySceneGlobalManager.addPlayerInScene(dungeonScene.getUid(), player);
    }

    private boolean isAllOnline(ITeam team) {
        return team.getMembers().stream()
                .allMatch(ITeamMember::isOnline);
    }

    private List<Player> getTeamMembers(ITeam team) {
        return team.getMembers().stream()
                .map(m -> playerGlobalManager.getPlayerByUid(m.getPlayerUid()))
                .collect(Collectors.toList());
    }

    private boolean isInSameScene(List<Player> players) {
        return players.stream()
                .collect(Collectors.groupingBy(Player::getSceneId))
                .size() == 1;
    }
}
