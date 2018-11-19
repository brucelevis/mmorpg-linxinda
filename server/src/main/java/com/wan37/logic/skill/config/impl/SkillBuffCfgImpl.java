package com.wan37.logic.skill.config.impl;

import com.wan37.logic.skill.config.SkillBuffCfg;

public class SkillBuffCfgImpl implements SkillBuffCfg {

    public SkillBuffCfgImpl(Integer id, double pro) {
        this.id = id;
        this.pro = pro;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public double getProbability() {
        return pro;
    }

    private final Integer id;
    private final double pro;
}
