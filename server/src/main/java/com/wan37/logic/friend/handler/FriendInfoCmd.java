package com.wan37.logic.friend.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.friend.service.FriendInfoExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class FriendInfoCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private FriendInfoExec friendInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        friendInfoExec.exec(player);
    }
}
