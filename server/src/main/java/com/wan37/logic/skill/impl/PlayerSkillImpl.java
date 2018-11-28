package com.wan37.logic.skill.impl;

import com.wan37.logic.skill.ISkill;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.database.PSkillDb;

class PlayerSkillImpl implements ISkill {

    public PlayerSkillImpl(SkillCfg skillCfg, PSkillDb skillDb) {
        this.skillCfg = skillCfg;
        this.skillDb = skillDb;
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
        return skillDb.getLevel();
    }

    @Override
    public long getLastUseTime() {
        return skillDb.getLastUseTime();
    }

    @Override
    public void setLastUseTime(long time) {
        skillDb.setLastUseTime(time);
    }

    @Override
    public int getCdInterval() {
        return skillCfg.getCd(skillDb.getLevel());
    }

    @Override
    public int getCostMp() {
        return skillCfg.getCostMp(skillDb.getLevel());
    }

    @Override
    public double getDemageAddition() {
        return skillCfg.getDemage(skillDb.getLevel());
    }

    @Override
    public SkillCfg getSkillCfg() {
        return skillCfg;
    }

    private final SkillCfg skillCfg;
    private final PSkillDb skillDb;
}
