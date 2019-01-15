package com.wan37.logic.trade.init;

import com.wan37.logic.player.Player;
import com.wan37.logic.trade.TradePlayer;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 生成交易中的玩家信息
 *
 * @author linda
 */
@Service
public class TradePlayerCreator {

    public TradePlayer create(Player player) {
        TradePlayer tradePlayer = new TradePlayer();
        tradePlayer.setPlayer(player);
        tradePlayer.setItems(new HashMap<>(0));
        tradePlayer.setCurrency(new HashMap<>(0));
        tradePlayer.setCommit(false);
        return tradePlayer;
    }
}
