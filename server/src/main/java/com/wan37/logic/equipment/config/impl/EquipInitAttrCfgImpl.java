package com.wan37.logic.equipment.config.impl;

import com.wan37.logic.equipment.config.EquipInitAttrCfg;

public class EquipInitAttrCfgImpl implements EquipInitAttrCfg {

    public EquipInitAttrCfgImpl(Integer attrCfgId, double value, double step, int maxRandInt) {
        this.attrCfgId = attrCfgId;
        this.value = value;
        this.step = step;
        this.maxRandInt = maxRandInt;
    }

    @Override
    public Integer getAttrCfgId() {
        return attrCfgId;
    }

    @Override
    public double getBaseValue() {
        return value;
    }

    @Override
    public double getStep() {
        return step;
    }

    @Override
    public int getMaxRandInt() {
        return maxRandInt;
    }

    private final Integer attrCfgId;
    private final double value;
    private final double step;
    private final int maxRandInt;
}
