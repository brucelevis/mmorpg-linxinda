package com.wan37.logic.friend.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.friend.service.FriendAcceptExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Friend_Accept implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private FriendAcceptExec friendAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        //FIXME: 冗余代码
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        Long id = msg.getParamAsLong(1);
        friendAcceptExec.exec(player, id);
    }
}
