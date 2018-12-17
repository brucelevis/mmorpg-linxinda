package com.wan37.logic.trade.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.GTrade;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.entity.TradePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeCloseExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player) {
        ITrade iTrade = player.getTrade();
        if (iTrade.getUid() == null) {
            throw new GeneralErrorExecption("你未在交易");
        }

        GTrade trade = tradeGlobalManager.getTrade(iTrade.getUid());
        if (trade == null) {
            throw new GeneralErrorExecption("交易不存在");
        }

        try {
            // 交易关闭
            trade.getLock().lock();

            trade.getTradePlayerMap().values().forEach(this::closeTrade);

            trade.getTradePlayerMap().clear();
            tradeGlobalManager.rmTrade(trade.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            trade.getLock().unlock();
        }
    }

    private void closeTrade(TradePlayer tradePlayer) {
        Player player = tradePlayer.getPlayer();
        player.getTrade().setUid(null);

        if (playerGlobalManager.isOnline(player.getUid())) {
            player.syncClient("交易关闭");
        }
    }
}
