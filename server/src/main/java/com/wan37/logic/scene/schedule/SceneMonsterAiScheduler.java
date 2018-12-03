package com.wan37.logic.scene.schedule;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.ai.MonsterAutoAttacker;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.ISkill;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SceneMonsterAiScheduler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private MonsterAutoAttacker monsterAutoAttacker;

    public void schedule(AbstractScene scene) {
        scene.getMonsters().stream()
                .filter(Monster::isAlive)
                .forEach(m -> autoAttack(m, scene));
    }

    private void autoAttack(Monster monster, AbstractScene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        ISkill skill = randSkill(monster, now);
        if (skill == null) {
            return;
        }

        if (skill.isEffectAll()) {
            // 全屏技能
            monsterAutoAttacker.attack(monster, scene.getPlayers(), skill, scene);
            return;
        }

        Long lastAttackId = monster.getLastAttackId();
        if (lastAttackId == null) {
            return;
        }

        Player player = playerGlobalManager.getPlayerByUid(lastAttackId);
        if (player == null) {
            return;
        }

        if (!Objects.equals(player.getSceneId(), monster.getSceneId())) {
            monster.setLastAttackId(null);
            return;
        }

        monsterAutoAttacker.attack(monster, player, skill, scene);
    }

    private ISkill randSkill(Monster monster, long now) {
        List<ISkill> skills = monster.getSkills().values().stream()
                .filter(i -> i.getLastUseTime() + i.getCdInterval() <= now)
                .collect(Collectors.toList());

        int size = skills.size();
        if (size == 0) {
            return null;
        }

        return skills.get(RandomUtil.rand(size));
    }
}
