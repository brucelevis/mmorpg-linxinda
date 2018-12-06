package com.wan37.logic.friend.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.friend.service.FriendAddExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Friend_Add implements GeneralHandler {

    @Autowired
    private FriendAddExec friendAddExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        friendAddExec.exec(player, uid);
    }
}