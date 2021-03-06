package com.wan37.logic.trade.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.event.OfflineEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.service.close.TradeCloser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易监听下线事件
 */
@Service
class TradeOnOffline implements GeneralEventListener<OfflineEvent> {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private TradeCloser tradeCloser;

    @Override
    public void execute(OfflineEvent offlineEvent) {
        Player player = offlineEvent.getPlayer();
        if (player.getTradeUid() == null) {
            // 未在交易
            return;
        }

        // 交易关闭
        Trade trade = tradeGlobalManager.getTrade(player.getTradeUid());
        tradeCloser.close(trade);

        playerDao.save(player.getPlayerDb());
    }
}
