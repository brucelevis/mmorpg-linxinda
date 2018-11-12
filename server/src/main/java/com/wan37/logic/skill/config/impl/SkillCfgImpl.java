package com.wan37.logic.skill.config.impl;

import com.wan37.config.entity.SkillCfgExcel;
import com.wan37.logic.skill.config.SkillCfg;

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
        //FIXME: 计算cd公式
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return cfgExcel.getMaxLevel();
    }

    @Override
    public double getDemage(int lv) {
        //FIXME: 计算公式
        return 2;
    }

    private final SkillCfgExcel cfgExcel;
}
