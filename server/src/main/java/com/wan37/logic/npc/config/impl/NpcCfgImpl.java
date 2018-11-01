package com.wan37.logic.npc.config.impl;

import com.wan37.config.entity.NpcCfgExcel;
import com.wan37.logic.npc.config.NpcCfg;

public class NpcCfgImpl implements NpcCfg {

    public NpcCfgImpl(NpcCfgExcel npcCfgExcel) {
        this.npcCfgExcel = npcCfgExcel;
    }

    @Override
    public Integer getId() {
        return npcCfgExcel.getId();
    }

    @Override
    public String getName() {
        return npcCfgExcel.getName();
    }

    private final NpcCfgExcel npcCfgExcel;
}
