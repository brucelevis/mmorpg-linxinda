package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.guild.service.donate.item.GuildDonateItemExec;
import com.wan37.logic.guild.service.donate.item.ReqGuildDonateItem;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会捐献实物
 */
@Service
class GuildDonateItemCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GuildDonateItemExec guildDonateItemExec;

    @Autowired
    private ReqGuildDonateItem.Factory reqLeagueDonateItemFactory;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        String args = msg.getParamAsString(1);
        ReqGuildDonateItem reqGuildDonateItem = reqLeagueDonateItemFactory.create(args);
        guildDonateItemExec.exec(player, reqGuildDonateItem);
    }
}
