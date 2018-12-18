package com.wan37.logic.trade.init;

import com.wan37.logic.player.Player;
import com.wan37.logic.trade.entity.TradePlayer;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TradePlayerCreator {

    public TradePlayer create(Player player) {
        TradePlayer tradePlayer = new TradePlayer();
        tradePlayer.setPlayer(player);
        tradePlayer.setItems(new HashMap<>());
        tradePlayer.setCurrency(new HashMap<>());
        return tradePlayer;
    }
}
