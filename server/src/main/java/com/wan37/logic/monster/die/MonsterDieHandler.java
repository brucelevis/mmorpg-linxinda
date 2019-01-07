package com.wan37.logic.monster.die;

import com.google.common.collect.ImmutableList;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.MonsterDieEvent;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.service.PlayerExpAdder;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.scene.base.SceneItem;
import com.wan37.logic.scene.encode.SceneItemEncoder;
import com.wan37.logic.summoning.Summoning;
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
    private GeneralEventListenersManager generalEventListenersManager;

    public void handle(Monster monster, long now) {
        synchronized (monster) {
            if (!monster.isAlive()) {
                return;
            }

            AbstractScene scene = sceneActorSceneGetter.get(monster);
            Long lastAttackUid = monster.getLastAttackId();

            Player lastAttacker = getLastAttacker(scene, lastAttackUid);
            //FIXME: 写死最后一击额外奖励（exp×10）
            playerExpAdder.add(lastAttacker, 10);

            // 结算经验：最后攻击的人所在的队伍在线且在当前场景里就能分到经验
            List<Player> playerList = getRelatedPlayerList(lastAttacker, scene.getId());
            int perExp = monster.getMonsterCfg().getExp() / playerList.size();
            playerList.forEach(p -> playerExpAdder.add(p, perExp));

            // 抛出击杀怪物事件
            playerList.forEach(p -> generalEventListenersManager.fireEvent(new MonsterDieEvent(p, monster)));

            // 怪物数据重置
            monster.setHp(0);
            monster.setAlive(false);
            monster.setDeadTime(now);
            monster.getBuffs().clear();
            monster.setLastAttackId(null);

            // 爆物
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
    }

    private Player getLastAttacker(AbstractScene scene, Long lastAttackerUid) {
        FightingUnit unit = scene.getTargetUnit(lastAttackerUid);
        if (unit instanceof Summoning) {
            // 是召唤兽杀的
            Summoning summoning = (Summoning) unit;
            return playerGlobalManager.getPlayerByUid(summoning.getBelongUid());
        }

        return (Player) unit;
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
