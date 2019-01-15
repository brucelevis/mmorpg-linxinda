package com.wan37.logic.skill.cast.behavior.behaviors;

import com.wan37.logic.skill.cast.FightingAttackHandler;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.scene.encode.FightingUnitEncoder;
import com.wan37.logic.skill.cast.after.FightingAfterHandler;
import com.wan37.logic.skill.cast.before.FightingUnitBeCastBeforeHandler;
import com.wan37.logic.skill.cast.before.FightingUnitSkillBeforeCastHandler;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicBehavior;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicContext;
import com.wan37.logic.skill.cast.check.FightingUnitBeCastFilters;
import com.wan37.logic.skill.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see com.wan37.logic.skill.SkillEffectLogicEnum#ATTACK
 */
@Service
class SkillEffectLogicBehavior1 implements SkillEffectLogicBehavior {

    @Autowired
    private FightingUnitEncoder fightingUnitEncoder;

    @Autowired
    private FightingAfterHandler fightingAfterHandler;

    @Autowired
    private FightingAttackHandler fightingAttackHandler;

    @Autowired
    private FightingUnitBeCastFilters fightingUnitBeCastFilters;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Autowired
    private FightingUnitSkillBeforeCastHandler fightingUnitSkillBeforeCastHandler;

    @Autowired
    private FightingUnitBeCastBeforeHandler fightingUnitBeCastBeforeHandler;

    @Override
    public void behave(SkillEffectLogicContext context) {
        FightingUnit caster = context.getCaster();
        Skill skill = context.getSkill();
        List<FightingUnit> targetList = fightingUnitBeCastFilters.filter(caster, context.getTargets());

        // 技能施放者施放前处理
        fightingUnitSkillBeforeCastHandler.handle(caster, skill);

        // 攻击
        AbstractScene scene = sceneActorSceneGetter.get(caster);
        targetList.forEach(u -> attackImpl(caster, u, skill, scene));

        // 攻击后
        targetList.forEach(u -> fightingAfterHandler.handle(caster, u, skill, scene));

        // 场景生物状态更新
        targetList.forEach(u -> updateNotify(scene, u));
    }

    private void attackImpl(FightingUnit caster, FightingUnit target, Skill skill, AbstractScene scene) {
        // 技能施放对象被施放前处理
        fightingUnitBeCastBeforeHandler.handle(caster, target);

        fightingAttackHandler.handle(caster, target, skill, scene);
    }

    private void updateNotify(AbstractScene scene, FightingUnit unit) {
        String msg = "状态更新推送|" + fightingUnitEncoder.encode(unit);
        scene.notify(msg);
    }
}
