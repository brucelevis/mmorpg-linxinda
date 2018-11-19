package com.wan37.logic.buff.config.impl;

import com.wan37.config.entity.BuffCfgExcel;
import com.wan37.logic.buff.config.BuffCfg;

public class BuffCfgImpl implements BuffCfg {

    public BuffCfgImpl(BuffCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;
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
    public Integer getEffectId() {
        return cfgExcel.getEffectId();
    }

    @Override
    public String getDesc() {
        return cfgExcel.getDesc();
    }

    @Override
    public boolean isOnce() {
        return cfgExcel.isOnce();
    }

    @Override
    public String getArg() {
        return cfgExcel.getArg();
    }

    @Override
    public int getInterval() {
        return cfgExcel.getInterval();
    }

    @Override
    public int getContinuous() {
        return cfgExcel.getContinuous();
    }

    private final BuffCfgExcel cfgExcel;
}
