package com.wan37.logic.monster.die;

import com.google.common.collect.ImmutableList;
import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.MonsterDieEvent;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.scene.SceneActorSceneGetter;
import com.wan37.logic.player.service.PlayerExpAdder;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.encode.SceneItemEncoder;
import com.wan37.logic.scene.item.SceneItem;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.ITeam;
import com.wan37.logic.team.entity.ITeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MonsterDieHandler {

    @Autowired
    private SceneItem.Factory sceneItemFactory;

    @Autowired
    private SceneItemEncoder sceneItemEncoder;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Autowired
    private PlayerExpAdder playerExpAdder;

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public void handle(Monster monster, long now) {
        Long lastAttackUid = monster.getLastAttackId();
        Integer sceneId = monster.getSceneId();

        // 结算经验：最后攻击的人所在的队伍在线且在当前场景里就能分到经验
        Player lastAttacker = playerGlobalManager.getPlayerByUid(lastAttackUid);
        List<Player> playerList = getRelatedPlayerList(lastAttacker, sceneId);
        int perExp = monster.getMonsterCfg().getExp() / playerList.size();
        playerList.forEach(p -> playerExpAdder.add(p, perExp));

        // 抛出击杀怪物事件
        playerList.forEach(p -> genernalEventListenersManager.fireEvent(new MonsterDieEvent(p, monster)));

        // 怪物数据重置
        monster.setHp(0);
        monster.setAlive(false);
        monster.setDeadTime(now);
        monster.getBuffs().clear();
        monster.setLastAttackId(null);

        // 爆物
        AbstractScene scene = sceneActorSceneGetter.get(monster);
        List<SceneItem> rewards = sceneItemFactory.create(monster.getMonsterCfg());
        if (!rewards.isEmpty()) {
            rewards.forEach(i -> scene.getItems().put(i.getUid(), i));

            // 通知玩家地上物品更新
            String head = String.format("%s怪物掉落：", monster.getName());
            String items = rewards.stream()
                    .map(i -> sceneItemEncoder.encode(i))
                    .collect(Collectors.joining("，"));
            scene.getPlayers().forEach(p -> p.syncClient(head + items));
        }
    }

    private List<Player> getRelatedPlayerList(Player player, Integer sceneId) {
        if (player.getTeamUid() == null) {
            return ImmutableList.of(player);
        }

        ITeam team = teamGlobalManager.getTeam(player.getTeamUid());
        return team.getMembers().stream()
                .filter(ITeamMember::isOnline)
                .map(m -> playerGlobalManager.getPlayerByUid(m.getPlayerUid()))
                .filter(p -> Objects.equals(p.getSceneId(), sceneId))
                .collect(Collectors.toList());
    }
}
