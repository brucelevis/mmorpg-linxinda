package com.wan37.logic.friend.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.friend.service.FriendRejectExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Friend_Reject implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private FriendRejectExec friendRejectExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long id = msg.getParamAsLong(1);
        friendRejectExec.exec(player, id);
    }
}
