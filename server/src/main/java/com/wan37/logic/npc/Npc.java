package com.wan37.logic.npc;

import com.wan37.logic.npc.config.NpcCfg;

/**
 * Npc实体类
 *
 * @author linda
 */
public class Npc {

    private NpcCfg npcCfg;

    public void setNpcCfg(NpcCfg npcCfg) {
        this.npcCfg = npcCfg;
    }

    public Integer getCfgId() {
        return npcCfg.getId();
    }

    public String getName() {
        return npcCfg.getName();
    }
}
