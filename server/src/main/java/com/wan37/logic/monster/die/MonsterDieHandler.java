package com.wan37.logic.monster.die;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.scene.SceneActorSceneGetter;
import com.wan37.logic.player.service.PlayerExpAdder;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.encode.SceneItemEncoder;
import com.wan37.logic.scene.item.SceneItem;
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

    public void handle(Monster monster, long now) {
        Long lastAttackUid = monster.getLastAttackId();
        Integer sceneId = monster.getSceneId();
        if (playerGlobalManager.isOnline(lastAttackUid)) {
            Player player = playerGlobalManager.getPlayerByUid(lastAttackUid);
            if (player != null && Objects.equals(player.getSceneId(), sceneId)) {
                // 还在当前场景的最后攻击怪物的人获得经验
                int exp = monster.getMonsterCfg().getExp();
                playerExpAdder.add(player, exp);
            }
        }

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
}
