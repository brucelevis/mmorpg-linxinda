package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.get.item.GuildGetItemExec;
import com.wan37.logic.guild.service.get.item.ReqGuildGetItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会取实物
 */
@Service
class GuildGetItemCmd implements GeneralHandler {

    @Autowired
    private GuildGetItemExec guildGetItemExec;

    @Autowired
    private ReqGuildGetItem.Factory reqLGetItemFactory;

    @Override
    public void handle(GeneralReqMsg msg) {
        ReqGuildGetItem reqGuildGetItem = reqLGetItemFactory.create(msg.getParamAsString(1));
        guildGetItemExec.exec(msg.getPlayer(), reqGuildGetItem);
    }
}
