package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.GuildQuitExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 离开公会
 */
@Service
class GuildQuitCmd implements GeneralHandler {

    @Autowired
    private GuildQuitExec guildQuitExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        guildQuitExec.exec(msg.getPlayer());
    }
}
