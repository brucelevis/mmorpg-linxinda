package com.wan37.logic.player.config.impl;

import com.wan37.config.excel.ExpCfgExcel;
import com.wan37.logic.player.config.ExpCfg;

/**
 * @author linda
 */
public class ExpCfgImpl implements ExpCfg {

    public ExpCfgImpl(ExpCfgExcel cfgExcel) {
        this.cfgExcel = cfgExcel;
    }

    @Override
    public int getId() {
        return cfgExcel.getId();
    }

    @Override
    public long getExp() {
        return cfgExcel.getExp();
    }

    private final ExpCfgExcel cfgExcel;
}
