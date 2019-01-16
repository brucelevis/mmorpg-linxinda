package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.GuildCreateExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建公会
 */
@Service
class GuildCreateCmd implements GeneralHandler {

    @Autowired
    private GuildCreateExec guildCreateExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String name = msg.getParamAsString(1);
        guildCreateExec.exec(msg.getPlayer(), name);
    }
}
