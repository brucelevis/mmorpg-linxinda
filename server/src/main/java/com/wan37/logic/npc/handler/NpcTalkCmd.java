package com.wan37.logic.npc.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.npc.service.NpcTalkExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Npc对话
 */
@Service
class NpcTalkCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private NpcTalkExec npcTalkExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Integer npcId = msg.getParamAsInt(1);
        npcTalkExec.exec(player, npcId);
    }
}
