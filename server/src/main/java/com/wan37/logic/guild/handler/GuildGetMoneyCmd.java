package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.GuildGetMoneyExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会取钱
 */
@Service
class GuildGetMoneyCmd implements GeneralHandler {

    @Autowired
    private GuildGetMoneyExec guildGetMoneyExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer cfgId = msg.getParamAsInt(1);
        long amount = msg.getParamAsLong(2);

        guildGetMoneyExec.exec(msg.getPlayer(), cfgId, amount);
    }
}
