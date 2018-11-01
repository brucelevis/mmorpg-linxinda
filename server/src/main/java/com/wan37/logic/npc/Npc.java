package com.wan37.logic.npc;

import com.wan37.logic.npc.config.NpcCfg;

public class Npc implements INpc {

    private NpcCfg npcCfg;

    public NpcCfg getNpcCfg() {
        return npcCfg;
    }

    public void setNpcCfg(NpcCfg npcCfg) {
        this.npcCfg = npcCfg;
    }

    @Override
    public Integer getCfgId() {
        return npcCfg.getId();
    }

    @Override
    public String getName() {
        return npcCfg.getName();
    }
}
