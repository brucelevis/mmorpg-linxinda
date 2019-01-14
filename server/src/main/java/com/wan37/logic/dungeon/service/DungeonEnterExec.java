package com.wan37.logic.dungeon.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.init.DungeonSceneCreator;
import com.wan37.logic.dungeon.scene.DungeonSceneAbstract;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.SceneTypeEnum;
import com.wan37.logic.scene.TemporarySceneGlobalManager;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.Team;
import com.wan37.logic.team.entity.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class DungeonEnterExec {

    @Autowired
    private ConfigLoader configLoader;

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
        SceneCfg sceneCfg = configLoader.load(SceneCfg.class, player.getSceneId()).orElse(null);
        if (sceneCfg == null) {
            player.syncClient("找不到当前场景配置");
            return;
        }

        if (!Objects.equals(sceneCfg.getType(), SceneTypeEnum.SCENE_TYPE_1.getId())) {
            player.syncClient("请切换到普通场景再尝试进入副本");
            return;
        }

        DungeonCfg dungeonCfg = configLoader.load(DungeonCfg.class, dungeonId).orElse(null);
        if (dungeonCfg == null) {
            player.syncClient("找不到相应的副本");
            return;
        }

        SceneCfg dungeonSceneCfg = configLoader.load(SceneCfg.class, dungeonCfg.getSceneId()).orElse(null);
        if (dungeonSceneCfg == null) {
            player.syncClient("找不到副本场景配置");
            return;
        }

        if (player.getTeamUid() == null) {
            player.syncClient("挑战副本需要创建队伍");
            return;
        }

        Team team = teamGlobalManager.getTeam(player.getTeamUid());
        if (!Objects.equals(team.getLeaderUid(), player.getUid())) {
            player.syncClient("只有队长才能发起副本挑战");
            return;
        }

        if (!isAllOnline(team)) {
            player.syncClient("有队员不在线");
            return;
        }

        List<Player> teamMembers = getTeamMembers(team);
        if (!isInSameScene(teamMembers)) {
            player.syncClient("队员不在同一场景");
            return;
        }

        // 创建副本场景
        DungeonSceneAbstract dungeonScene = dungeonSceneCreator.create(dungeonCfg, dungeonSceneCfg);
        temporarySceneGlobalManager.addScene(dungeonScene);

        teamMembers.forEach(p -> enterDungeon(p, dungeonScene));
    }

    private void enterDungeon(Player player, DungeonSceneAbstract dungeonScene) {
        // 离开普通场景
        sceneFacade.leaveScene(player);

        String msg = String.format("[%s副本]挑战开始\n", dungeonScene.getDungeonCfg().getName());
        player.syncClient(msg);

        // 进入副本场景
        temporarySceneGlobalManager.addPlayerInScene(dungeonScene.getUid(), player);
    }

    private boolean isAllOnline(Team team) {
        return team.getMembers().stream()
                .allMatch(TeamMember::isOnline);
    }

    private List<Player> getTeamMembers(Team team) {
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
