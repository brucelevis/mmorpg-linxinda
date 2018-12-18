package com.wan37.logic.trade.service.close;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.player.Player;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.GTrade;
import com.wan37.logic.trade.entity.ITrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeCloseExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private TradeCloser tradeCloser;

    public void exec(Player player) {
        ITrade iTrade = player.getTrade();
        if (iTrade.getUid() == null) {
            throw new GeneralErrorExecption("你未在交易");
        }

        GTrade trade = tradeGlobalManager.getTrade(iTrade.getUid());
        if (trade == null) {
            throw new GeneralErrorExecption("交易不存在");
        }

        tradeCloser.close(trade);
    }
}