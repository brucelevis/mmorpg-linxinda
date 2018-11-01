package com.wan37.logic.scene.config.impl;

import com.wan37.logic.scene.config.SceneMonsterCfg;

public class SceneMonsterCfgImpl implements SceneMonsterCfg {

    public SceneMonsterCfgImpl(Integer cfgId, int amount) {
        this.cfgId = cfgId;
        this.amount = amount;
    }

    @Override
    public Integer getCfgId() {
        return cfgId;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    private final Integer cfgId;
    private final int amount;
}
