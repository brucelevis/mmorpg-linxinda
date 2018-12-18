package com.wan37.logic.trade.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.OfflineEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.dao.PlayerDao;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.GTrade;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.service.close.TradeCloser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ITrade iTrade = player.getTrade();
        if (iTrade.getUid() == null) {
            return;
        }

        GTrade trade = tradeGlobalManager.getTrade(iTrade.getUid());
        if (trade == null) {
            return;
        }

        tradeCloser.close(trade);
        playerDao.save(player.getPlayerDb());
    }
}
