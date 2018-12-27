package com.wan37.logic.monster.ai;

import com.google.common.collect.ImmutableList;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.skill.SkillTargetTypeEnum;
import com.wan37.logic.skill.config.SkillCfg;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 获取怪物施放技能目标对象
 */
@Service
public class MonsterSkillCastTargetsGetter {

    public List<FightingUnit> get(Monster caster, SkillCfg skillCfg, AbstractScene scene) {
        Integer targetType = skillCfg.getTargetType();
        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_1.getId())) {
            // 自己
            return ImmutableList.of(caster);
        }

        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_2.getId())) {
            // 友方
            if (skillCfg.isEffectAll()) {
                // 群体技能
                return scene.getMonsters().stream()
                        .filter(Monster::isAlive)
                        .collect(Collectors.toList());
            }

            // 这里不是群体施放默认给自己
            return ImmutableList.of(caster);
        }

        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_3.getId())) {
            // 对特定目标
            if (skillCfg.isEffectAll()) {
                // AOE 群体伤害玩家
                return scene.getPlayers().stream()
                        .filter(Player::isAlive)
                        .collect(Collectors.toList());
            }

            Long lastAttackerUid = caster.getLastAttackId();
            if (lastAttackerUid == null) {
                return ImmutableList.of();
            }

            return scene.getPlayers().stream()
                    .filter(p -> Objects.equals(p.getUid(), lastAttackerUid))
                    .filter(Player::isAlive)
                    .collect(Collectors.toList());
        }

        return ImmutableList.of();
    }
}
