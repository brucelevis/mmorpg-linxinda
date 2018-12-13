package com.wan37.logic.league.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.league.service.LeagueGetMoneyExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class League_GetMoney implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private LeagueGetMoneyExec leagueGetMoneyExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        //FIXME: 代码重复
        Integer cfgId = msg.getParamAsInt(1);
        long amount = msg.getParamAsLong(2);
        leagueGetMoneyExec.exec(player, cfgId, amount);
    }
}
