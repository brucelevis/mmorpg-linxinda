package com.wan37.logic.equipment.config.impl;

import com.wan37.logic.equipment.config.EquipInitAttrCfg;

public class EquipInitAttrCfgImpl implements EquipInitAttrCfg {

    public EquipInitAttrCfgImpl(Integer attrCfgId, double value) {
        this.attrCfgId = attrCfgId;
        this.value = value;
    }

    @Override
    public Integer getAttrCfgId() {
        return attrCfgId;
    }

    @Override
    public double getValue() {
        return value;
    }

    private final Integer attrCfgId;
    private final double value;
}
