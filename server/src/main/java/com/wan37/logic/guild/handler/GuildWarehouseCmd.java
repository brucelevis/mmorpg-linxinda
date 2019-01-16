package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.guild.service.GuildWarehouseExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会仓库信息
 */
@Service
class GuildWarehouseCmd implements GeneralHandler {

    @Autowired
    private GuildWarehouseExec guildWarehouseExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        guildWarehouseExec.exec(msg.getPlayer());
    }
}
