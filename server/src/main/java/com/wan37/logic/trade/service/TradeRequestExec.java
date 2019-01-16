package com.wan37.logic.trade.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.init.TradePlayerCreator;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class TradeRequestExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private TradePlayerCreator tradePlayerCreator;

    @Autowired
    private SceneFacade sceneFacade;

    public void exec(Player player, Player target) {
        if (target.getTradeUid() != null) {
            player.syncClient("对方正在交易中，不能发起交易请求");
            return;
        }

        if (player.getTradeUid() != null) {
            player.syncClient("你正在交易中，不能发起交易请求");
            return;
        }

        if (!sceneFacade.isInSameScene(player, target)) {
            player.syncClient("目标不在同一场景，无法发起交易请求");
            return;
        }

        // 创建交易
        Trade trade = createTrade(player, target);
        tradeGlobalManager.addTrade(trade);

        // 发起者玩家设置正在交易的uid
        player.setTradeUid(trade.getUid());

        // 推送
        player.syncClient(String.format("你向[%s]发起了交易请求", target.getName()));
        target.syncClient(String.format("[%s]向你发起了交易请求（tradeUid：%s）", player.getName(), trade.getUid()));
    }

    private Trade createTrade(Player inviter, Player target) {
        Trade trade = new Trade();

        trade.setUid(IdUtil.generate());
        trade.setInviterUid(inviter.getUid());
        trade.setTargetUid(target.getUid());

        trade.getTradePlayerMap().put(inviter.getUid(), tradePlayerCreator.create(inviter));
        return trade;
    }
}
