package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.donate.item.GuildDonateItemExec;
import com.wan37.logic.guild.service.donate.item.ReqGuildDonateItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会捐献实物
 */
@Service
class GuildDonateItemCmd implements GeneralHandler {

    @Autowired
    private GuildDonateItemExec guildDonateItemExec;

    @Autowired
    private ReqGuildDonateItem.Factory reqLeagueDonateItemFactory;

    @Override
    public void handle(GeneralReqMsg msg) {
        String args = msg.getParamAsString(1);
        ReqGuildDonateItem reqGuildDonateItem = reqLeagueDonateItemFactory.create(args);

        guildDonateItemExec.exec(msg.getPlayer(), reqGuildDonateItem);
    }
}
