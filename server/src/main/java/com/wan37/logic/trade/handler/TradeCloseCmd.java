package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.service.close.TradeCloseExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易关闭
 */
@Service
class TradeCloseCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private TradeCloseExec tradeCloseExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        tradeCloseExec.exec(player);
    }
}
