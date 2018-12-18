package com.wan37.logic.trade.service.close;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.GTrade;
import com.wan37.logic.trade.entity.TradePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TradeCloser {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private ResourceFacade resourceFacade;

    public void close(GTrade trade) {
        trade.getTradePlayerMap().values().forEach(this::closeTrade);

        trade.getTradePlayerMap().clear();
        tradeGlobalManager.rmTrade(trade.getUid());
    }

    private void closeTrade(TradePlayer tradePlayer) {
        Player player = tradePlayer.getPlayer();
        player.getTrade().setUid(null);

        // 归还物品
        backpackFacade.add(player, new ArrayList<>(tradePlayer.getItems().values()));

        // 归还钱
        resourceFacade.giveResource(new ResourceCollectionImpl(tradePlayer.getCurrency().entrySet().stream()
                .map(e -> new ResourceElementImpl(e.getKey(), e.getValue()))
                .collect(Collectors.toList())), player);
    }
}
