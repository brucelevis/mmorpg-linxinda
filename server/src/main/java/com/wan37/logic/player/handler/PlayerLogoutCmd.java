package com.wan37.logic.player.handler;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.OfflineEvent;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登出
 */
@Service
class PlayerLogoutCmd implements GeneralHandler {

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        generalEventListenersManager.fireEvent(new OfflineEvent(player));
        player.syncClient("下线成功");
    }
}
