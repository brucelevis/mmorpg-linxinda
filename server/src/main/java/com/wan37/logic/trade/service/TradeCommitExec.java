package com.wan37.logic.trade.service;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.TradeSuccessEvent;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.TradePlayer;
import com.wan37.logic.trade.service.close.TradeCloser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class TradeCommitExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private TradeCloser tradeCloser;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void exec(Player player) {
        if (player.getTradeUid() == null) {
            player.syncClient("未在交易");
            return;
        }

        Trade trade = tradeGlobalManager.getTrade(player.getTradeUid());
        if (trade == null) {
            player.syncClient("交易不存在");
            return;
        }

        // 设置提交交易标志
        TradePlayer tradePlayer = trade.getTradePlayerMap().get(player.getUid());
        tradePlayer.setCommit(true);

        // 推送
        String notify = String.format("[%s]确认了交易", player.getName());
        trade.getTradePlayerMap().values().forEach(p -> p.getPlayer().syncClient(notify));

        if (!isAllCommit(trade)) {
            return;
        }

        // 交易判断
        TradePlayer inviter = trade.getTradePlayerMap().get(trade.getInviterUid());
        TradePlayer target = trade.getTradePlayerMap().get(trade.getTargetUid());
        if (!canExchange(inviter, target)) {
            // 交易关闭逻辑
            tradeCloser.close(trade);
            return;
        }

        // 开始执行交易交换物品逻辑
        Player targetPlayer = target.getPlayer();
        backpackFacade.add(targetPlayer, new ArrayList<>(inviter.getItems().values()));
        resourceFacade.giveResource(getTradeVirtualItem(inviter), targetPlayer);


        Player inviterPlayer = inviter.getPlayer();
        backpackFacade.add(inviterPlayer, new ArrayList<>(target.getItems().values()));
        resourceFacade.giveResource(getTradeVirtualItem(target), inviterPlayer);

        finishTrade(trade);

        // 抛出交易成功事件
        generalEventListenersManager.fireEvent(new TradeSuccessEvent(inviterPlayer));
        generalEventListenersManager.fireEvent(new TradeSuccessEvent(targetPlayer));

        // 交易成功推送
        inviterPlayer.syncClient("交易成功");
        targetPlayer.syncClient("交易成功");
    }

    private boolean isAllCommit(Trade trade) {
        return trade.getTradePlayerMap().values().stream()
                .allMatch(TradePlayer::isCommit);
    }

    private boolean canExchange(TradePlayer inviter, TradePlayer target) {
        Player inviterPlayer = inviter.getPlayer();
        Player targetPlayer = target.getPlayer();

        int toSpareCapacity = backpackFacade.getSpareCapacity(targetPlayer);
        if (inviter.getItems().size() > toSpareCapacity) {
            String notify = String.format("[%s]背包空间不够", targetPlayer.getName());
            notify(notify, inviterPlayer, targetPlayer);
            return false;
        }

        int fromSpareCapacity = backpackFacade.getSpareCapacity(inviterPlayer);
        if (target.getItems().size() > fromSpareCapacity) {
            String notify = String.format("[%s]背包空间不够", inviterPlayer.getName());
            notify(notify, inviterPlayer, targetPlayer);
            return false;
        }

        return true;
    }

    private void notify(String msg, Player... players) {
        for (Player player : players) {
            player.syncClient(msg);
        }
    }

    private void finishTrade(Trade trade) {
        trade.getTradePlayerMap().values()
                .forEach(p -> p.getPlayer().setTradeUid((null)));

        trade.getTradePlayerMap().clear();
        tradeGlobalManager.rmTrade(trade.getUid());
    }

    private ResourceCollection getTradeVirtualItem(TradePlayer tradePlayer) {
        return new ResourceCollectionImpl(tradePlayer.getCurrency().entrySet().stream()
                .map(e -> new ResourceElementImpl(e.getKey(), e.getValue()))
                .collect(Collectors.toList()));
    }
}
