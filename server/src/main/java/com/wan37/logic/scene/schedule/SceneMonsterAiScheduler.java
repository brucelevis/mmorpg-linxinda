package com.wan37.logic.scene.schedule;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.ai.MonsterAutoAttacker;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SceneMonsterAiScheduler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private MonsterAutoAttacker monsterAutoAttacker;

    public void schedule(Scene scene) {
        scene.getMonsters().stream()
                .filter(Monster::isAlive)
                .filter(m -> m.getLastAttackId() != null)
                .forEach(m -> autoAttack(m, scene));
    }

    private void autoAttack(Monster monster, Scene scene) {
        Player player = playerGlobalManager.getPlayerByUid(monster.getLastAttackId());
        if (player == null) {
            return;
        }

        if (!Objects.equals(player.getSceneId(), monster.getSceneId())) {
            monster.setLastAttackId(null);
            return;
        }

        monsterAutoAttacker.attack(monster, player, scene);
    }
}
