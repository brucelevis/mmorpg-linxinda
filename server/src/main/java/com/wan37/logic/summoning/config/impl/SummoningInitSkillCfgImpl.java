package com.wan37.logic.summoning.config.impl;

import com.wan37.logic.summoning.config.SummoningInitSkillCfg;

class SummoningInitSkillCfgImpl implements SummoningInitSkillCfg {

    public SummoningInitSkillCfgImpl(Integer id, int level) {
        this.id = id;
        this.level = level;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int getLevel() {
        return level;
    }

    private final Integer id;
    private final int level;
}
