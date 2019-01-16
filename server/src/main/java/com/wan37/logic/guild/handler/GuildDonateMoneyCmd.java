package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.donate.money.LeagueDonateMoneyExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会捐钱
 */
@Service
class GuildDonateMoneyCmd implements GeneralHandler {

    @Autowired
    private LeagueDonateMoneyExec leagueDonateMoneyExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer id = msg.getParamAsInt(1);
        long amount = msg.getParamAsLong(2);

        leagueDonateMoneyExec.exec(msg.getPlayer(), id, amount);
    }
}
