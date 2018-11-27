package com.wan37.logic.monster.die;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
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
    private SceneGlobalManager sceneGlobalManager;

    public void handle(Monster monster, long now) {
        Long lastAttackUid = monster.getLastAttackId();
        Player player = playerGlobalManager.getPlayerByUid(lastAttackUid);

        monster.setHp(0);
        monster.setAlive(false);
        monster.setDeadTime(now);
        monster.getBuffs().clear();
        monster.setLastAttackId(null);

        Integer sceneId = monster.getSceneId();
        Scene scene = sceneGlobalManager.getScene(sceneId);

        if (player != null && Objects.equals(player.getSceneId(), sceneId)) {
            // 还在当前场景的最后攻击怪物的人获得经验
            int exp = monster.getMonsterCfg().getExp();
            PlayerDb playerDb = player.getPlayerDb();
            playerDb.setExp(playerDb.getExp() + exp);

            player.syncClient(String.format("获得%s经验", exp));
        }

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
