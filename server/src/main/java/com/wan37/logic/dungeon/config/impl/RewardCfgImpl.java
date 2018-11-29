package com.wan37.logic.dungeon.config.impl;

import com.wan37.logic.dungeon.config.DungeonRewardCfg;

class RewardCfgImpl implements DungeonRewardCfg {

    public RewardCfgImpl(Integer id, int amount, double pro) {
        this.id = id;
        this.amount = amount;
        this.pro = pro;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public double getProbability() {
        return pro;
    }

    private final Integer id;
    private final int amount;
    private final double pro;
}
