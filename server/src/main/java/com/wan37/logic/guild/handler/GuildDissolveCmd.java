package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.GuildDissolveExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会解散
 */
@Service
class GuildDissolveCmd implements GeneralHandler {

    @Autowired
    private GuildDissolveExec guildDissolveExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        guildDissolveExec.exec(msg.getPlayer());
    }
}
