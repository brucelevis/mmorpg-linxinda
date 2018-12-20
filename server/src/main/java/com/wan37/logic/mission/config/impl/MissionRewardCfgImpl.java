package com.wan37.logic.mission.config.impl;

import com.wan37.logic.mission.config.MissionRewardCfg;

class MissionRewardCfgImpl implements MissionRewardCfg {

    public MissionRewardCfgImpl(Integer id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    private final Integer id;
    private final int amount;
}
