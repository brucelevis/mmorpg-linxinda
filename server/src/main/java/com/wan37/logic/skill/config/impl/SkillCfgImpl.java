package com.wan37.logic.skill.config.impl;

import com.google.common.collect.ImmutableMap;
import com.wan37.config.entity.SkillCfgExcel;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.util.ScriptEngineUtil;

import java.util.Map;

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
        Map<String, Object> bindgings = createCdBindging(lv);
        double result = ScriptEngineUtil.eval(cfgExcel.getCd(), bindgings);
        return (int) Math.round(result);
    }

    @Override
    public int getMaxLevel() {
        return cfgExcel.getMaxLevel();
    }

    @Override
    public double getDemage(int lv) {
        Map<String, Object> bindgings = createDemageBindging(lv);
        return ScriptEngineUtil.eval(cfgExcel.getDemage(), bindgings);
    }

    private Map<String, Object> createCdBindging(int lv) {
        return ImmutableMap.of("lv", lv);
    }

    private Map<String, Object> createDemageBindging(int lv) {
        return ImmutableMap.of("lv", lv);
    }

    private final SkillCfgExcel cfgExcel;
}
