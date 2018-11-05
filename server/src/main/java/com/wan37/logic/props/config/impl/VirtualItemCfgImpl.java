package com.wan37.logic.props.config.impl;

import com.wan37.config.entity.VirtualItemCfgExcel;
import com.wan37.logic.props.config.VirtualItemCfg;

public class VirtualItemCfgImpl implements VirtualItemCfg {

    public VirtualItemCfgImpl(VirtualItemCfgExcel cfgExcel) {
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
    public long getMaxOverlay() {
        return cfgExcel.getMaxOverlay();
    }

    private final VirtualItemCfgExcel cfgExcel;
}
