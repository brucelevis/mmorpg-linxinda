package com.wan37.logic.trade.init;

import com.wan37.logic.player.Player;
import com.wan37.logic.trade.entity.TradePlayer;
import org.springframework.stereotype.Service;

@Service
public class TradePlayerCreator {

    public TradePlayer create(Player player) {
        TradePlayer tradePlayer = new TradePlayer();
        tradePlayer.setPlayer(player);
        return tradePlayer;
    }
}
