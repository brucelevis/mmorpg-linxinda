package com.wan37.logic.trade.encode;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.backpack.encode.BackpackItemSimpleInfoEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.TradePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 交易信息编码
 *
 * @author linda
 */
@Service
public class TradeEncoder {

    @Autowired
    private BackpackItemSimpleInfoEncoder backpackItemSimpleInfoEncoder;

    @Autowired
    private ConfigLoader configLoader;

    public String encode(Trade trade) {
        String head = "交易信息更新：\n";
        String msg = trade.getTradePlayerMap().values().stream()
                .map(this::encodeTradePlayer)
                .collect(Collectors.joining("\n"));
        return head + msg;
    }

    private String encodeTradePlayer(TradePlayer tradePlayer) {
        Player player = tradePlayer.getPlayer();
        String head = String.format("[%s]的交易列表：\n", player.getName());

        String items = tradePlayer.getItems().values().stream()
                .map(i -> backpackItemSimpleInfoEncoder.encode(i))
                .collect(Collectors.joining("\n"));

        String currency = tradePlayer.getCurrency().entrySet().stream()
                .map(e -> encodeCurrency(e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));

        return head + items + "\n" + currency;
    }

    private String encodeCurrency(Integer cfgId, long amount) {
        return String.format("%s：%s", configLoader.loadName(VirtualItemCfg.class, cfgId), amount);
    }
}
