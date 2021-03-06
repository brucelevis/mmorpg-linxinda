package com.wan37.logic.npc.config.impl;

import com.wan37.config.excel.NpcCfgExcel;
import com.wan37.logic.npc.config.NpcCfg;

/**
 * @author linda
 */
public class NpcCfgImpl implements NpcCfg {

    public NpcCfgImpl(NpcCfgExcel cfgExcel) {
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
    public String getTalk() {
        return cfgExcel.getTalk();
    }

    private final NpcCfgExcel cfgExcel;
}
