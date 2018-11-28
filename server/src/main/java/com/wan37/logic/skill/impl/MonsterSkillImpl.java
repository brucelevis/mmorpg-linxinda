package com.wan37.logic.skill.impl;

import com.wan37.logic.skill.ISkill;
import com.wan37.logic.skill.config.SkillCfg;

class MonsterSkillImpl implements ISkill {

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
    public double getDemageAddition() {
        return skillCfg.getDemage(level);
    }

    private final SkillCfg skillCfg;
    private final int level;

    private long lastUseTime;
}
