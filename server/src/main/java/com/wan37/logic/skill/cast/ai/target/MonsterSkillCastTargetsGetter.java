package com.wan37.logic.skill.cast.ai.target;

import com.google.common.collect.ImmutableList;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.skill.SkillTargetTypeEnum;
import com.wan37.logic.skill.config.SkillCfg;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 获取怪物施放技能目标对象
 *
 * @author linda
 */
@Service
public class MonsterSkillCastTargetsGetter {

    public List<FightingUnit> get(Monster caster, SkillCfg skillCfg, AbstractScene scene) {
        Integer targetType = skillCfg.getTargetType();
        if (Objects.equals(targetType, SkillTargetTypeEnum.MYSELF.getId())) {
            // 自己
            return ImmutableList.of(caster);
        }

        if (Objects.equals(targetType, SkillTargetTypeEnum.TEAM.getId())) {
            // 友方
            if (skillCfg.isEffectAll()) {
                // 群体技能
                return new ArrayList<>(scene.getMonsters());
            }

            // 这里不是群体施放默认给自己
            return ImmutableList.of(caster);
        }

        if (Objects.equals(targetType, SkillTargetTypeEnum.ENEMY.getId())) {
            // 对特定目标
            if (skillCfg.isEffectAll()) {
                // AOE 群体伤害玩家
                return new ArrayList<>(scene.getPlayers());
            }

            Long lastAttackerUid = caster.getLastAttackId();
            if (lastAttackerUid == null) {
                return ImmutableList.of();
            }

            FightingUnit target = scene.getTargetUnit(lastAttackerUid);
            if (target == null) {
                return ImmutableList.of();
            }

            return ImmutableList.of(target);
        }

        return ImmutableList.of();
    }
}
