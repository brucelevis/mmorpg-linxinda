package com.wan37.logic.faction.config.impl;

import com.wan37.config.entity.FactionCfgExcel;
import com.wan37.logic.faction.config.FactionCfg;
import com.wan37.logic.faction.config.FactionInitAttrCfg;

import java.util.List;

public class FactionCfgImpl implements FactionCfg {

    public FactionCfgImpl(FactionCfgExcel cfgExcel) {
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
    public List<FactionInitAttrCfg> getInitAttrs() {
        return null;
    }

    private final FactionCfgExcel cfgExcel;
}
