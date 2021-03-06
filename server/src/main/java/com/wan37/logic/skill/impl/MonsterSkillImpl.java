package com.wan37.logic.skill.impl;

import com.wan37.logic.skill.Skill;
import com.wan37.logic.skill.config.SkillCfg;

class MonsterSkillImpl implements Skill {

    public MonsterSkillImpl(SkillCfg skillCfg, int level) {
        this.skillCfg = skillCfg;
        this.level = level;
    }

    @Override
    public Integer getId() {
        return skillCfg.getId();
    }

    @Override
    public String getName() {
        return skillCfg.getName();
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public long getLastUseTime() {
        return lastUseTime;
    }

    @Override
    public void setLastUseTime(long time) {
        lastUseTime = time;
    }

    @Override
    public int getCdInterval() {
        return skillCfg.getCd(level);
    }

    @Override
    public int getCostMp() {
        return skillCfg.getCostMp(level);
    }

    @Override
    public double getEffectValue() {
        return skillCfg.getEffectValue(level);
    }

    @Override
    public SkillCfg getSkillCfg() {
        return skillCfg;
    }

    private final SkillCfg skillCfg;
    private final int level;

    private long lastUseTime;
}
