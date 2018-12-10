package com.wan37.logic.league.handler;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.league.service.LeagueChangeExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class League_Change implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private LeagueChangeExec leagueChangeExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        Player target = playerGlobalManager.getPlayerByUid(uid);
        if (target == null) {
            throw new GeneralErrorExecption("目标不存在");
        }

        leagueChangeExec.exec(player, target);
    }
}
