package com.wan37.logic.trade.service.close;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceCollection;
import com.wan37.logic.props.resource.impl.ResourceCollectionImpl;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.TradePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 交易结束逻辑
 *
 * @author linda
 */
@Service
public class TradeCloser {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void close(Trade trade) {
        trade.getTradePlayerMap().values().forEach(this::closeTradeImpl);
        tradeGlobalManager.rmTrade(trade.getUid());
    }

    private void closeTradeImpl(TradePlayer tradePlayer) {
        Player player = tradePlayer.getPlayer();
        player.setTradeUid(null);

        // 归还物品
        returnBackpackItem(player, tradePlayer);

        // 归还钱
        returnVirtualItem(player, tradePlayer);

        // 推送
        if (playerGlobalManager.isOnline(player.getUid())) {
            player.syncClient("交易关闭");
        }
    }

    private void returnBackpackItem(Player player, TradePlayer tradePlayer) {
        backpackFacade.add(player, new ArrayList<>(tradePlayer.getItems().values()));
    }

    private void returnVirtualItem(Player player, TradePlayer tradePlayer) {
        ResourceCollection res = getTradeVirtualItem(tradePlayer);
        resourceFacade.giveResource(res, player);
    }

    private ResourceCollection getTradeVirtualItem(TradePlayer tradePlayer) {
        return new ResourceCollectionImpl(tradePlayer.getCurrency().entrySet().stream()
                .map(e -> new ResourceElementImpl(e.getKey(), e.getValue()))
                .collect(Collectors.toList()));
    }
}
