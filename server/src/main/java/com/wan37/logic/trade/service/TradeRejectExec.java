package com.wan37.logic.trade.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.Trade;
import com.wan37.logic.trade.entity.TradePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class TradeRejectExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player, Long tradeUid) {
        Trade trade = tradeGlobalManager.getTrade(tradeUid);
        if (trade == null) {
            player.syncClient("交易已关闭");
            return;
        }

        if (!Objects.equals(trade.getToUid(), player.getUid())) {
            player.syncClient("错误的交易uid");
            return;
        }

        // 拒绝交易
        player.syncClient("你拒绝了交易");

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
        from.syncClient(String.format("[%s]拒绝了你的交易请求", player.getName()));

        from.getTrade().setUid(null);
        tradeGlobalManager.rmTrade(tradeUid);
    }
}
