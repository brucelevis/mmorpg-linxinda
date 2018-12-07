package com.wan37.logic.league.handler;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.league.service.LeagueAddExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class League_Add implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private LeagueAddExec leagueAddExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        if (!playerGlobalManager.isOnline(uid)) {
            throw new GeneralErrorExecption("对方不在线，公会邀请只能在线才能添加");
        }

        Player target = playerGlobalManager.getPlayerByUid(uid);
        leagueAddExec.exec(player, target);
    }
}
