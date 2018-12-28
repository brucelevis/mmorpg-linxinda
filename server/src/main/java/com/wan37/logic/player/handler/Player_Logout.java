package com.wan37.logic.player.handler;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.entity.OfflineEvent;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Player_Logout implements GeneralHandler {

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        genernalEventListenersManager.fireEvent(new OfflineEvent(player));
        player.syncClient("下线成功");
    }
}
