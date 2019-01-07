package com.wan37.logic.trade.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.GTrade;
import com.wan37.logic.trade.entity.TradePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TradeRejectExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player to, Long tradeUid) {
        GTrade trade = tradeGlobalManager.getTrade(tradeUid);
        if (trade == null) {
            throw new GeneralErrorException("交易已关闭");
        }

        if (!Objects.equals(trade.getToUid(), to.getUid())) {
            throw new GeneralErrorException("错误的交易uid");
        }

        // 拒绝交易
        to.syncClient("你拒绝了交易");

        Long fromUid = trade.getFromUid();
        TradePlayer fromTradePlayer = trade.getTradePlayerMap().get(fromUid);
        if (fromTradePlayer == null) {
            // 请求者取消交易
            return;
        }

        if (!playerGlobalManager.isOnline(fromUid)) {
            // 没在线
            return;
        }

        Player from = fromTradePlayer.getPlayer();
        from.syncClient(String.format("[%s]拒绝了你的交易请求", to.getName()));

        from.getTrade().setUid(null);
        tradeGlobalManager.rmTrade(tradeUid);
    }
}
