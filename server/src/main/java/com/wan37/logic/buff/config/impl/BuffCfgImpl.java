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
    public Integer getType1() {
        return cfgExcel.getType1();
    }

    @Override
    public Integer getType2() {
        return cfgExcel.getType2();
    }

    @Override
    public String getDesc() {
        return cfgExcel.getDesc();
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
