package com.wan37.logic.buff.config.impl;

import com.wan37.config.excel.BuffCfgExcel;
import com.wan37.logic.buff.config.BuffCfg;

/**
 * Buff配置表加载器
 *
 * @author linda
 */
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

    @Override
    public Integer getTarget() {
        return cfgExcel.getTarget();
    }

    private final BuffCfgExcel cfgExcel;
}
