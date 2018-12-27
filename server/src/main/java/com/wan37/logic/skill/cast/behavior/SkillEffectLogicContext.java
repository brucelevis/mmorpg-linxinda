package com.wan37.logic.skill.cast.behavior;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.skill.entity.ISkill;

import java.util.List;

public class SkillEffectLogicContext {

    public SkillEffectLogicContext(FightingUnit caster, ISkill skill, List<FightingUnit> targets) {
        this.caster = caster;
        this.skill = skill;
        this.targets = targets;
    }

    public FightingUnit getCaster() {
        return caster;
    }

    public ISkill getSkill() {
        return skill;
    }

    public List<FightingUnit> getTargets() {
        return targets;
    }

    private final FightingUnit caster;
    private final ISkill skill;
    private final List<FightingUnit> targets;
}
