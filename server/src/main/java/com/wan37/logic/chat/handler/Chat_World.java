package com.wan37.logic.chat.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.chat.service.ChatWorldExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Chat_World implements GeneralHandler {

    @Autowired
    private ChatWorldExec chatWorldExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        String content = msg.getParams()[1];
        chatWorldExec.exec(player, content);
    }
}
