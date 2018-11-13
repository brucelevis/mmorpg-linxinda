package com.wan37.logic.monster.config.impl;

import com.wan37.logic.monster.config.MonsterItemCfg;

public class MonsterItemCfgImpl implements MonsterItemCfg {

    public MonsterItemCfgImpl(Integer cfgId, int amount, double probability) {
        this.cfgId = cfgId;
        this.amount = amount;
        this.probability = probability;
    }

    @Override
    public Integer getCfgId() {
        return cfgId;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public double getProbability() {
        return probability;
    }

    private final Integer cfgId;
    private final int amount;
    private final double probability;
}
