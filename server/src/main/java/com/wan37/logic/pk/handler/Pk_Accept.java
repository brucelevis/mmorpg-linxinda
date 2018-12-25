package com.wan37.logic.pk.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.pk.service.PkAcceptExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Pk_Accept implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PkAcceptExec pkAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        pkAcceptExec.exec(player, uid);
    }
}
