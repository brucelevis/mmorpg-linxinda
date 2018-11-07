package com.wan37.logic.faction.config.impl;

import com.wan37.logic.faction.config.FactionInitAttrCfg;

class FactionInitAttrCfgImpl implements FactionInitAttrCfg {

    public FactionInitAttrCfgImpl(Integer attrCfgId, double value) {
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
