package com.wan37.logic.chat.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.chat.service.ChatPrivateExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 私聊
 */
@Service
class ChatPrivateCmd implements GeneralHandler {

    @Autowired
    private ChatPrivateExec chatPrivateExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        String[] params = msg.getParams();
        Long uid = Long.parseLong(params[1]);
        String content = params[2];

        chatPrivateExec.exec(player, uid, content);
    }
}
