package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.service.add.item.TradeAddItemExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易添加实物
 */
@Service
class TradeAddItemCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private TradeAddItemExec tradeAddItemExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Integer index = msg.getParamAsInt(1);
        int amount = msg.getParamAsInt(2);
        tradeAddItemExec.exec(player, index, amount);
    }
}
