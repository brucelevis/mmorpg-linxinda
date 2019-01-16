package com.wan37.logic.npc.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.npc.service.NpcTalkExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Npc对话
 */
@Service
class NpcTalkCmd implements GeneralHandler {

    @Autowired
    private NpcTalkExec npcTalkExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer npcId = msg.getParamAsInt(1);
        npcTalkExec.exec(msg.getPlayer(), npcId);
    }
}
