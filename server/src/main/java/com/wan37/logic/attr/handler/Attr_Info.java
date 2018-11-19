package com.wan37.logic.attr.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.attr.service.AttrInfoExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Attr_Info implements GeneralHandler {

    @Autowired
    private AttrInfoExec attrInfoExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        attrInfoExec.exec(player);
    }
}
