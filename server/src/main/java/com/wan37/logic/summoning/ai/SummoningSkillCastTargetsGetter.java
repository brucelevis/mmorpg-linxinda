package com.wan37.logic.summoning.ai;

import com.google.common.collect.ImmutableList;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.scene.SceneTypeEnum;
import com.wan37.logic.skill.SkillTargetTypeEnum;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.summoning.Summoning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 获取召唤兽施放技能目标对象
 */
@Service
public class SummoningSkillCastTargetsGetter {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public List<FightingUnit> get(Summoning summoning, SkillCfg skillCfg, AbstractScene scene) {
        Integer targetType = skillCfg.getTargetType();
        Long masterUid = summoning.getBelongUid();
        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_1.getId())) {
            // 自己
            return ImmutableList.of(summoning);
        }

        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_2.getId())) {
            // 友方
            if (!playerGlobalManager.isOnline(masterUid)) {
                return ImmutableList.of(summoning);
            }

            Player master = playerGlobalManager.getPlayerByUid(masterUid);
            if (skillCfg.isEffectAll()) {
                // 群体技能（自己和主人）
                return ImmutableList.of(master, summoning);
            }

            // 这里不是群体施放默认给主人
            return ImmutableList.of(master);
        }

        if (Objects.equals(targetType, SkillTargetTypeEnum.SKILL_TARGET_TYPE_3.getId())) {
            // 对特定目标
            if (Objects.equals(scene.getType(), SceneTypeEnum.SCENE_TYPE_3.getId())) {
                // 在竞技场
                return scene.getPlayers().stream()
                        .filter(Player::isAlive)
                        .filter(p -> !Objects.equals(p.getUid(), masterUid))
                        .collect(Collectors.toList());
            }

            if (skillCfg.isEffectAll()) {
                // AOE 群体伤害
                return new ArrayList<>(scene.getMonsters());
            }

            // 随机
            FightingUnit target = randTargetUnit(scene);
            if (target == null) {
                return ImmutableList.of();
            }

            return ImmutableList.of(target);
        }

        return ImmutableList.of();
    }

    private FightingUnit randTargetUnit(AbstractScene scene) {
        return scene.getMonsters().stream()
                .filter(Monster::isAlive)
                .findAny()
                .orElse(null);
    }
}
