package com.wan37.logic.skill.cast.behavior.behaviors;

import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.player.service.FightingUnitHpAdder;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.scene.encode.FightingUnitEncoder;
import com.wan37.logic.skill.cast.before.FightingUnitSkillBeforeCastHandler;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicBehavior;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicContext;
import com.wan37.logic.skill.cast.check.FightingUnitBeCastFilters;
import com.wan37.logic.skill.entity.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see com.wan37.logic.skill.SkillEffectLogicEnum#SKILL_EFFECT_LOGIC_3
 */
@Service
class SkillEffectLogicBehav3 implements SkillEffectLogicBehavior {

    @Autowired
    private FightingUnitBeCastFilters fightingUnitBeCastFilters;

    @Autowired
    private FightingUnitSkillBeforeCastHandler fightingUnitSkillBeforeCastHandler;

    @Autowired
    private FightingUnitEncoder fightingUnitEncoder;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Autowired
    private FightingUnitHpAdder fightingUnitHpAdder;

    @Override
    public void behave(SkillEffectLogicContext context) {
        FightingUnit caster = context.getCaster();
        ISkill skill = context.getSkill();
        List<FightingUnit> targetList = fightingUnitBeCastFilters.filter(caster, context.getTargets());

        // 技能施放者施放前处理
        fightingUnitSkillBeforeCastHandler.handle(caster, skill);

        AbstractScene scene = sceneActorSceneGetter.get(caster);
        long addHp = (long) skill.getEffectValue();
        targetList.forEach(u -> cure(caster, u, skill, scene, addHp));

        targetList.forEach(u -> updateNotify(scene, u));
    }

    private void cure(FightingUnit caster, FightingUnit target, ISkill skill, AbstractScene scene, long addHp) {
        fightingUnitHpAdder.add(target, addHp);

        String msg = String.format("[%s]施放[%s]治疗了[%s]%s点hp", caster.getName(), skill.getName(), target.getName(), addHp);
        scene.notify(msg);
    }

    private void updateNotify(AbstractScene scene, FightingUnit unit) {
        String msg = "状态更新推送|" + fightingUnitEncoder.encode(unit);
        scene.notify(msg);
    }
}