package com.wan37.logic.skill.config.impl;

import com.wan37.config.entity.SkillCfgExcel;
import com.wan37.logic.skill.SkillCfg;

public class SkillCfgImpl implements SkillCfg {

    public SkillCfgImpl(SkillCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;
    }

    @Override
    public Integer getId() {
        return cfgExcel.getId();
    }

    @Override
    public String getName() {
        return cfgExcel.getName();
    }

    @Override
    public String getDesc() {
        return cfgExcel.getDesc();
    }

    @Override
    public int getCd(int lv) {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return cfgExcel.getMaxLevel();
    }

    private final SkillCfgExcel cfgExcel;
}
