package com.wan37.logic.npc.init;

import com.wan37.logic.npc.Npc;
import com.wan37.logic.npc.config.NpcCfg;
import org.springframework.stereotype.Service;

@Service
public class NpcCreator {

    public Npc create(NpcCfg npcCfg) {
        Npc npc = new Npc();
        npc.setNpcCfg(npcCfg);
        return npc;
    }
}
