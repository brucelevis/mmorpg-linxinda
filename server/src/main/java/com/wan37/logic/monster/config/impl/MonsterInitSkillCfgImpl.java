package com.wan37.logic.monster.config.impl;

import com.wan37.logic.monster.config.MonsterInitSkillCfg;

class MonsterInitSkillCfgImpl implements MonsterInitSkillCfg {

    public MonsterInitSkillCfgImpl(Integer id, int level) {
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
