package com.wan37.logic.team.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.service.TeamAcceptExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 同意加入组队
 */
@Service
class TeamAcceptCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private TeamAcceptExec teamAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long teamUid = msg.getParamAsLong(1);
        teamAcceptExec.exec(player, teamUid);
    }
}
