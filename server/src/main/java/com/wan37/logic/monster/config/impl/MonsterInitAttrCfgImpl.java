package com.wan37.logic.monster.config.impl;

import com.wan37.logic.monster.config.MonsterInitAttrCfg;

public class MonsterInitAttrCfgImpl implements MonsterInitAttrCfg {

    public MonsterInitAttrCfgImpl(Integer id, double value) {
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
