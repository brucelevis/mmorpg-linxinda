package com.wan37.logic.trade.service;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.TradeSuccessEvent;
import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.GTrade;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.entity.TradePlayer;
import com.wan37.logic.trade.service.close.TradeCloser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    private GenernalEventListenersManager genernalEventListenersManager;

    public void exec(Player player) {
        ITrade iTrade = player.getTrade();
        if (iTrade.getUid() == null) {
            throw new GeneralErrorExecption("未在交易");
        }

        GTrade trade = tradeGlobalManager.getTrade(iTrade.getUid());
        if (trade == null) {
            throw new GeneralErrorExecption("交易不存在");
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
            genernalEventListenersManager.fireEvent(new TradeSuccessEvent(fromPlayer));
            genernalEventListenersManager.fireEvent(new TradeSuccessEvent(toPlayer));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            trade.getLock().unlock();
        }
    }

    private boolean isAllCommit(GTrade trade) {
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

    private void finishTrade(GTrade trade) {
        trade.getTradePlayerMap().values()
                .forEach(p -> p.getPlayer().getTrade().setUid(null));

        trade.getTradePlayerMap().clear();
        tradeGlobalManager.rmTrade(trade.getUid());
    }
}
