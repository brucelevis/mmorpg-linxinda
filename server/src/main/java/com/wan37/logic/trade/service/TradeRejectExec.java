package com.wan37.logic.trade.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.service.close.TradeCloser;
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

    @Autowired
    private TradeCloser tradeCloser;

    public void exec(Player player, Long tradeUid) {
        Trade trade = tradeGlobalManager.getTrade(tradeUid);
        if (trade == null) {
            player.syncClient("交易已关闭");
            return;
        }

        if (!Objects.equals(trade.getTargetUid(), player.getUid())) {
            player.syncClient("错误的交易uid");
            return;
        }

        // 交易关闭
        tradeCloser.close(trade);

        // 推送
        player.syncClient("你拒绝了交易");

        Long inviterUid = trade.getInviterUid();
        Player inviter = trade.getTradePlayerMap().get(inviterUid).getPlayer();
        if (playerGlobalManager.isOnline(inviter.getUid())) {
            inviter.syncClient(String.format("[%s]拒绝了你的交易请求", player.getName()));
        }
    }
}
