package com.wan37.logic.skill.config.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.wan37.config.entity.SkillCfgExcel;
import com.wan37.logic.skill.config.SkillBuffCfg;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.util.ScriptEngineUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public int getCostMp(int lv) {
        Map<String, Object> bindgings = createCostMpBindging(lv);
        double result = ScriptEngineUtil.eval(cfgExcel.getCostMp(), bindgings);
        return (int) Math.round(result);
    }

    @Override
    public List<SkillBuffCfg> getBuffs() {
        if (cfgExcel.getBuffs() == null || "".equals(cfgExcel.getBuffs())) {
            return ImmutableList.of();
        }

        return Arrays.stream(cfgExcel.getBuffs().split(","))
                .map(this::createBuff)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEffectAll() {
        return cfgExcel.isEffectAll();
    }

    private SkillBuffCfg createBuff(String s) {
        String[] buff = s.split(":");
        Integer id = Integer.parseInt(buff[0]);
        double val = Double.parseDouble(buff[1]);

        return new SkillBuffCfgImpl(id, val);
    }

    private Map<String, Object> createCdBindging(int lv) {
        return ImmutableMap.of("lv", lv);
    }

    private Map<String, Object> createDemageBindging(int lv) {
        return ImmutableMap.of("lv", lv);
    }

    private Map<String, Object> createCostMpBindging(int lv) {
        return ImmutableMap.of("lv", lv);
    }

    private final SkillCfgExcel cfgExcel;
}
