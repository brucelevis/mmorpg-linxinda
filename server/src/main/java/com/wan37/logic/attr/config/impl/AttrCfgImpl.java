package com.wan37.logic.attr.config.impl;

import com.wan37.config.entity.AttrCfgExcel;
import com.wan37.logic.attr.config.AttrCfg;

public class AttrCfgImpl implements AttrCfg {

    public AttrCfgImpl(AttrCfgExcel cfgExcel) {
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
    public String getDesc() {
        return cfgExcel.getDesc();
    }

    private final AttrCfgExcel cfgExcel;
}
