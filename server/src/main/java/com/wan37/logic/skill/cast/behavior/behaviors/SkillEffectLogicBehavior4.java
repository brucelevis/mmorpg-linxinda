package com.wan37.logic.skill.cast.behavior.behaviors;

import com.wan37.logic.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.scene.encode.FightingUnitEncoder;
import com.wan37.logic.skill.cast.after.FightingAfterHandler;
import com.wan37.logic.skill.cast.before.FightingUnitSkillBeforeCastHandler;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicBehavior;
import com.wan37.logic.skill.cast.behavior.SkillEffectLogicContext;
import com.wan37.logic.skill.cast.check.FightingUnitBeCastFilters;
import com.wan37.logic.skill.entity.Skill;
import com.wan37.logic.summoning.config.SummoningCfg;
import com.wan37.logic.summoning.Summoning;
import com.wan37.logic.summoning.init.SummoningCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see com.wan37.logic.skill.SkillEffectLogicEnum#SKILL_EFFECT_LOGIC_4
 */
@Service
class SkillEffectLogicBehavior4 implements SkillEffectLogicBehavior {

    @Autowired
    private FightingUnitBeCastFilters fightingUnitBeCastFilters;

    @Autowired
    private FightingUnitSkillBeforeCastHandler fightingUnitSkillBeforeCastHandler;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    @Autowired
    private SummoningCfgLoader summoningCfgLoader;

    @Autowired
    private SummoningCreator summoningCreator;

    @Autowired
    private FightingAfterHandler fightingAfterHandler;

    @Autowired
    private FightingUnitEncoder fightingUnitEncoder;

    @Override
    public void behave(SkillEffectLogicContext context) {
        FightingUnit caster = context.getCaster();
        Skill skill = context.getSkill();
        List<FightingUnit> targetList = fightingUnitBeCastFilters.filter(caster, context.getTargets());

        // 技能施放者施放前处理
        fightingUnitSkillBeforeCastHandler.handle(caster, skill);

        AbstractScene scene = sceneActorSceneGetter.get(caster);
        Integer cfgId = (int) skill.getEffectValue();
        SummoningCfg summoningCfg = summoningCfgLoader.load(cfgId)
                .orElseThrow(() -> new RuntimeException("找不到召唤兽配置表"));

        // 创建召唤兽
        Summoning summoning = summoningCreator.create(caster.getUid(), summoningCfg, scene);
        scene.getSummonings().add(summoning);

        String summoningNotify = String.format("[%s]施放了[%s]召唤出了[%s]", caster.getName(), skill.getName(), summoning.getName());
        scene.notify(summoningNotify);

        // 施放后
        targetList.forEach(u -> fightingAfterHandler.handle(caster, u, skill, scene));

        // 场景生物状态更新
        targetList.forEach(u -> updateNotify(scene, u));
    }

    private void updateNotify(AbstractScene scene, FightingUnit unit) {
        String msg = "状态更新推送|" + fightingUnitEncoder.encode(unit);
        scene.notify(msg);
    }
}
