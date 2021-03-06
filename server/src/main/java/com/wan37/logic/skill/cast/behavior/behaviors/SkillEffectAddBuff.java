package com.wan37.logic.skill.cast.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.scene.encode.FightingUnitEncoder;
import com.wan37.logic.skill.cast.after.FightingAfterHandler;
import com.wan37.logic.skill.cast.before.FightingUnitSkillBeforeCastHandler;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicBehavior;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicContext;
import com.wan37.logic.skill.cast.check.FightingUnitBeCastFilters;
import com.wan37.logic.skill.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see com.wan37.logic.skill.SkillEffectLogicEnum#ADD_BUFF
 */
@Service
@BehaviorLogic(id = 2)
class SkillEffectAddBuff implements SkillEffectLogicBehavior {

    @Autowired
    private FightingUnitBeCastFilters fightingUnitBeCastFilters;

    @Autowired
    private FightingUnitSkillBeforeCastHandler fightingUnitSkillBeforeCastHandler;

    @Autowired
    private FightingUnitEncoder fightingUnitEncoder;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Autowired
    private FightingAfterHandler fightingAfterHandler;

    @Override
    public void behave(SkillEffectLogicContext context) {
        FightingUnit caster = context.getCaster();
        Skill skill = context.getSkill();
        List<FightingUnit> targetList = fightingUnitBeCastFilters.filter(caster, context.getTargets());

        // 技能施放者施放前处理
        fightingUnitSkillBeforeCastHandler.handle(caster, skill);

        AbstractScene scene = sceneActorSceneGetter.get(caster);
        targetList.forEach(u -> skillEffect(caster, u, skill, scene));

        targetList.forEach(u -> updateNotify(scene, u));
    }

    private void skillEffect(FightingUnit caster, FightingUnit target, Skill skill, AbstractScene scene) {
        String msg = String.format("[%s]对[%s]施放了[%s]", caster.getName(), target.getName(), skill.getName());
        scene.notify(msg);

        fightingAfterHandler.handle(caster, target, skill, scene);
    }

    private void updateNotify(AbstractScene scene, FightingUnit unit) {
        String msg = "状态更新推送|" + fightingUnitEncoder.encode(unit);
        scene.notify(msg);
    }
}