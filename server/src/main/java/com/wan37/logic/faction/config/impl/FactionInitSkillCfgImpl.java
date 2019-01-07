package com.wan37.logic.faction.config.impl;

import com.wan37.logic.faction.config.FactionInitSkillCfg;

/**
 * @author linda
 */
public class FactionInitSkillCfgImpl implements FactionInitSkillCfg {

    public FactionInitSkillCfgImpl(Integer skillCfgId, int level) {
        this.skillCfgId = skillCfgId;
        this.level = level;
    }

    @Override
    public Integer getSkillCfgId() {
        return skillCfgId;
    }

    @Override
    public int getLevel() {
        return level;
    }

    private final Integer skillCfgId;
    private final int level;
}
