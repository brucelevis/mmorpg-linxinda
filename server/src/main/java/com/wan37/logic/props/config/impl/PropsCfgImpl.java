package com.wan37.logic.props.config.impl;

import com.wan37.config.entity.PropsCfgExcel;
import com.wan37.logic.props.config.PropsCfg;

/**
 * @author linda
 */
public class PropsCfgImpl implements PropsCfg {

    public PropsCfgImpl(PropsCfgExcel cfgExcel) {
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
    public int getMaxOverLay() {
        return cfgExcel.getMaxOverlay();
    }

    @Override
    public boolean isCanUse() {
        return cfgExcel.isCanUse();
    }

    @Override
    public Integer getUseLogicId() {
        return cfgExcel.getUseLogicId();
    }

    @Override
    public String getUseLogicArgs() {
        return cfgExcel.getUseLogicArgs();
    }

    @Override
    public Integer getType() {
        return cfgExcel.getType();
    }

    private final PropsCfgExcel cfgExcel;
}
