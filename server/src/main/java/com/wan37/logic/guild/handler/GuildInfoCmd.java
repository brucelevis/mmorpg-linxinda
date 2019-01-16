package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.GuildInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会信息
 */
@Service
class GuildInfoCmd implements GeneralHandler {

    @Autowired
    private GuildInfoExec guildInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        guildInfoExec.exec(msg.getPlayer());
    }
}
