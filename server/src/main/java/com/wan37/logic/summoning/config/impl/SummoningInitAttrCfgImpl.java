package com.wan37.logic.summoning.config.impl;

import com.wan37.logic.summoning.config.SummoningInitAttrCfg;

class SummoningInitAttrCfgImpl implements SummoningInitAttrCfg {

    public SummoningInitAttrCfgImpl(Integer id, double value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public double getValue() {
        return value;
    }

    private final Integer id;
    private final double value;
}
