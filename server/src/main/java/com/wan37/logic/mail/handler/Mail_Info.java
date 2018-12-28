package com.wan37.logic.mail.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.mail.service.MailInfoExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Mail_Info implements GeneralHandler {

    @Autowired
    private MailInfoExec mailInfoExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        mailInfoExec.exec(player);
    }
}
