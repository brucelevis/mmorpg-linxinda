package com.wan37.logic.league.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.league.service.get.item.LeagueGetItemExec;
import com.wan37.logic.league.service.get.item.ReqLGetItem;
import com.wan37.logic.mail.service.send.MailSendExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class League_GetItem implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private LeagueGetItemExec leagueGetItemExec;

    @Autowired
    private ReqLGetItem.Factory reqLGetItemFactory;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        ReqLGetItem reqLGetItem = reqLGetItemFactory.create(msg.getParamAsString(1));
        leagueGetItemExec.exec(player, reqLGetItem);
    }
}
