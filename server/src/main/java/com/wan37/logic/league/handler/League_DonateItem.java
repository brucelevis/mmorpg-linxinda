package com.wan37.logic.league.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.league.service.donate.item.LeagueDonateItemExec;
import com.wan37.logic.league.service.donate.item.ReqLDonateItem;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class League_DonateItem implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private LeagueDonateItemExec leagueDonateItemExec;

    @Autowired
    private ReqLDonateItem.Factory reqLeagueDonateItemFactory;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        String args = msg.getParamAsString(1);
        ReqLDonateItem reqLDonateItem = reqLeagueDonateItemFactory.create(args);
        leagueDonateItemExec.exec(player, reqLDonateItem);
    }
}
