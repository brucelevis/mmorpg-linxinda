package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.guild.service.GuildGetMoneyExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会取钱
 */
@Service
class GuildGetMoneyCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GuildGetMoneyExec guildGetMoneyExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        //FIXME: 代码重复
        Integer cfgId = msg.getParamAsInt(1);
        long amount = msg.getParamAsLong(2);
        guildGetMoneyExec.exec(player, cfgId, amount);
    }
}
