package com.wan37.logic.skill.cast.behavior;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.skill.entity.Skill;

import java.util.List;

/**
 * @author linda
 */
public class SkillEffectLogicContext {

    public SkillEffectLogicContext(FightingUnit caster, Skill skill, List<FightingUnit> targets) {
        this.caster = caster;
        this.skill = skill;
        this.targets = targets;
    }

    public FightingUnit getCaster() {
        return caster;
    }

    public Skill getSkill() {
        return skill;
    }

    public List<FightingUnit> getTargets() {
        return targets;
    }

    private final FightingUnit caster;
    private final Skill skill;
    private final List<FightingUnit> targets;
}
