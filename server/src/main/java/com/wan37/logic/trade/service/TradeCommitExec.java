package com.wan37.logic.trade.service;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.TradeSuccessEvent;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.entity.Trade;
import com.wan37.logic.trade.entity.TradePlayer;
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
        ITrade iTrade = player.getTrade();
        if (iTrade.getUid() == null) {
            player.syncClient("未在交易");
            return;
        }

        Trade trade = tradeGlobalManager.getTrade(iTrade.getUid());
        if (trade == null) {
            player.syncClient("交易不存在");
            return;
        }

        try {
            trade.getLock().lock();

            TradePlayer tradePlayer = trade.getTradePlayerMap().get(player.getUid());
            tradePlayer.setCommit(true);

            String notify = String.format("[%s]确认了交易", player.getName());
            trade.getTradePlayerMap().values().forEach(p -> p.getPlayer().syncClient(notify));

            if (!isAllCommit(trade)) {
                return;
            }

            // 交易判断
            TradePlayer from = trade.getTradePlayerMap().get(trade.getFromUid());
            TradePlayer to = trade.getTradePlayerMap().get(trade.getToUid());

            if (!canExchange(from, to)) {
                // 交易关闭逻辑
                tradeCloser.close(trade);
                return;
            }

            Player toPlayer = to.getPlayer();
            backpackFacade.add(toPlayer, new ArrayList<>(from.getItems().values()));
            resourceFacade.giveResource(new ResourceCollectionImpl(from.getCurrency().entrySet().stream()
                    .map(e -> new ResourceElementImpl(e.getKey(), e.getValue()))
                    .collect(Collectors.toList())), toPlayer);


            Player fromPlayer = from.getPlayer();
            backpackFacade.add(fromPlayer, new ArrayList<>(to.getItems().values()));
            resourceFacade.giveResource(new ResourceCollectionImpl(to.getCurrency().entrySet().stream()
                    .map(e -> new ResourceElementImpl(e.getKey(), e.getValue()))
                    .collect(Collectors.toList())), fromPlayer);

            fromPlayer.syncClient("交易成功");
            toPlayer.syncClient("交易成功");

            finishTrade(trade);

            // 抛出交易成功事件
            generalEventListenersManager.fireEvent(new TradeSuccessEvent(fromPlayer));
            generalEventListenersManager.fireEvent(new TradeSuccessEvent(toPlayer));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            trade.getLock().unlock();
        }
    }

    private boolean isAllCommit(Trade trade) {
        return trade.getTradePlayerMap().values().stream()
                .allMatch(TradePlayer::isCommit);
    }

    private boolean canExchange(TradePlayer from, TradePlayer to) {
        Player fromPlayer = from.getPlayer();
        Player toPlayer = to.getPlayer();

        int toSpareCapacity = backpackFacade.getSpareCapacity(toPlayer);
        if (from.getItems().size() > toSpareCapacity) {
            String notify = String.format("[%s]背包空间不够", toPlayer.getName());
            notify(notify, fromPlayer, toPlayer);
            return false;
        }

        int fromSpareCapacity = backpackFacade.getSpareCapacity(fromPlayer);
        if (to.getItems().size() > fromSpareCapacity) {
            String notify = String.format("[%s]背包空间不够", fromPlayer.getName());
            notify(notify, fromPlayer, toPlayer);
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
                .forEach(p -> p.getPlayer().getTrade().setUid(null));

        trade.getTradePlayerMap().clear();
        tradeGlobalManager.rmTrade(trade.getUid());
    }
}
