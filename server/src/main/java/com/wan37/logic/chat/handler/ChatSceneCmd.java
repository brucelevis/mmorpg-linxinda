package com.wan37.logic.chat.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.chat.service.ChatSceneExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 当前场景聊天
 */
@Service
class ChatSceneCmd implements GeneralHandler {

    @Autowired
    private ChatSceneExec chatSceneExec;

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
        chatSceneExec.exec(player, params[1]);
    }
}
