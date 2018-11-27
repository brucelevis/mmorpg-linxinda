package com.wan37.logic.scene.schedule;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.ai.MonsterAutoAttacker;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.Scene;
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
                .forEach(this::autoAttack);
    }

    private void autoAttack(Monster monster) {
        Player player = playerGlobalManager.getPlayerByUid(monster.getLastAttackId());
        if (player == null) {
            return;
        }

        if (!Objects.equals(player.getSceneId(), monster.getSceneId())) {
            return;
        }

        monsterAutoAttacker.attack(monster, player);
    }
}
