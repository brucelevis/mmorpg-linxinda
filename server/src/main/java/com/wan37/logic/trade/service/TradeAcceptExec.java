package com.wan37.logic.trade.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.init.TradePlayerCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class TradeAcceptExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private TradePlayerCreator tradePlayerCreator;

    @Autowired
    private SceneFacade sceneFacade;

    public void exec(Player player, Long tradeUid) {
        if (player.getTradeUid() != null) {
            player.syncClient("你正在交易中，不能接受交易请求");
            return;
        }

        Trade trade = tradeGlobalManager.getTrade(tradeUid);
        if (trade == null) {
            player.syncClient("交易已关闭");
            return;
        }

        if (!Objects.equals(trade.getTargetUid(), player.getUid())) {
            player.syncClient("错误的交易uid");
            return;
        }

        Long inviterUid = trade.getInviterUid();
        Player inviter = trade.getTradePlayerMap().get(inviterUid).getPlayer();
        if (!sceneFacade.isInSameScene(player, inviter)) {
            player.syncClient("不在同一场景");
            return;
        }

        // 接受交易
        player.setTradeUid(tradeUid);
        trade.getTradePlayerMap().put(player.getUid(), tradePlayerCreator.create(player));

        // 推送
        inviter.syncClient(String.format("[%s]接受了你的交易请求", player.getName()));
        player.syncClient(String.format("你接受了[%s]的交易请求", inviter.getName()));
    }
}