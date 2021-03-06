package com.wan37.logic.trade.service.close;

import com.wan37.logic.player.Player;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class TradeCloseExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private TradeCloser tradeCloser;

    public void exec(Player player) {
        if (player.getTradeUid() == null) {
            player.syncClient("你未在交易");
            return;
        }

        Trade trade = tradeGlobalManager.getTrade(player.getTradeUid());
        if (trade == null) {
            player.syncClient("交易不存在");
            return;
        }

        tradeCloser.close(trade);
    }
}