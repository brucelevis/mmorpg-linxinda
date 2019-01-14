package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.service.TradeRequestExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易请求申请
 */
@Service
class TradeRequestCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private TradeRequestExec tradeRequestExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        if (!playerGlobalManager.isOnline(uid)) {
            player.syncClient("对方不在线");
            return;
        }

        Player target = playerGlobalManager.getPlayerByUid(uid);
        tradeRequestExec.exec(player, target);
    }
}
