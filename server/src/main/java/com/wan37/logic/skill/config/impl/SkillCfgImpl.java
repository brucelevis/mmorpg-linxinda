package com.wan37.logic.skill.config.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.wan37.config.excel.SkillCfgExcel;
import com.wan37.logic.skill.config.SkillBuffCfg;
import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.util.ScriptEngineUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author linda
 */
public class SkillCfgImpl implements SkillCfg {

    public SkillCfgImpl(SkillCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;

        buffs = initBuffs();
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
        Map<String, Object> binding = createCdBinding(lv);
        double result = ScriptEngineUtil.eval(cfgExcel.getCd(), binding);
        return (int) Math.round(result);
    }

    @Override
    public double getEffectValue(int lv) {
        Map<String, Object> binding = createDamageBinding(lv);
        return ScriptEngineUtil.eval(cfgExcel.getEffectValue(), binding);
    }

    @Override
    public int getCostMp(int lv) {
        Map<String, Object> binding = createCostMpBinding(lv);
        double result = ScriptEngineUtil.eval(cfgExcel.getCostMp(), binding);
        return (int) Math.round(result);
    }

    @Override
    public List<SkillBuffCfg> getBuffs() {
        return buffs;
    }

    @Override
    public boolean isEffectAll() {
        return cfgExcel.isEffectAll();
    }

    @Override
    public Integer getEffectLogic() {
        return cfgExcel.getEffectLogic();
    }

    @Override
    public Integer getTargetType() {
        return cfgExcel.getTargetType();
    }

    private SkillBuffCfg createBuff(String s) {
        String[] buff = s.split(":");
        Integer id = Integer.parseInt(buff[0]);
        double val = Double.parseDouble(buff[1]);

        return new SkillBuffCfgImpl(id, val);
    }

    private Map<String, Object> createCdBinding(int lv) {
        return ImmutableMap.of("lv", lv);
    }

    private Map<String, Object> createDamageBinding(int lv) {
        return ImmutableMap.of("lv", lv);
    }

    private Map<String, Object> createCostMpBinding(int lv) {
        return ImmutableMap.of("lv", lv);
    }

    private List<SkillBuffCfg> initBuffs() {
        if (cfgExcel.getBuffs() == null) {
            return ImmutableList.of();
        }

        return Arrays.stream(cfgExcel.getBuffs().split(","))
                .map(this::createBuff)
                .collect(Collectors.toList());
    }

    private final SkillCfgExcel cfgExcel;
    private List<SkillBuffCfg> buffs;
}
