package com.wan37.logic.attr.config.impl;

import com.wan37.config.excel.AttrCfgExcel;
import com.wan37.logic.attr.config.AttrCfg;

/**
 * @author linda
 */
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

    @Override
    public double getBaseAttackValue() {
        return cfgExcel.getBaseAttackValue();
    }

    @Override
    public double getBaseDefenseValue() {
        return cfgExcel.getBaseDefenseValue();
    }

    @Override
    public int getEquipBaseScore() {
        return cfgExcel.getEquipBaseScore();
    }

    private final AttrCfgExcel cfgExcel;
}
