package com.wan37.logic.pk.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.pk.service.PkRejectExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拒绝决斗
 */
@Service
class PkRejectCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PkRejectExec pkRejectExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        pkRejectExec.exec(player, uid);
    }
}
