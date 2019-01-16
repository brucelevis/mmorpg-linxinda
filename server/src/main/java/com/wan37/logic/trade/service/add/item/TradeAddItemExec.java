package com.wan37.logic.trade.service.add.item;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.encode.TradeEncoder;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.TradePlayer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author linda
 */
@Service
public class TradeAddItemExec {

    /**
     * FIXME: 写死交易格子
     */
    private static final int MAX_CAPACITY = 9;

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private BackpackFacade backpackFacade;

    @Autowired
    private TradeEncoder tradeEncoder;

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    public void exec(Player player, Integer index, int amount) {
        if (amount <= 0) {
            player.syncClient("要交易的物品数必须为正数");
            return;
        }

        if (player.getTradeUid() == null) {
            player.syncClient("未在交易");
            return;
        }

        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        ItemDb itemDb = backpackFacade.find(backpackDb, index).orElse(null);
        if (itemDb == null) {
            player.syncClient(("找不到对应的背包格子物品"));
            return;
        }

        if (itemDb.getAmount() < amount) {
            player.syncClient("背包物品数量不足");
            return;
        }

        Trade trade = tradeGlobalManager.getTrade(player.getTradeUid());
        if (trade == null) {
            player.syncClient("交易不存在");
            return;
        }

        TradePlayer myTradePlayer = trade.getTradePlayerMap().get(player.getUid());
        if (myTradePlayer.getItems().values().size() >= MAX_CAPACITY) {
            player.syncClient("交易框已满");
            return;
        }

        // 复制物品进交易栏
        ItemDb tradeItem = new ItemDb();
        BeanUtils.copyProperties(itemDb, tradeItem);
        tradeItem.setAmount(amount);
        Integer newIndex = findEmptyIndex(myTradePlayer.getItems());
        tradeItem.setIndex(newIndex);
        myTradePlayer.getItems().put(newIndex, tradeItem);

        // 扣除背包物品
        backpackFacade.remove(backpackDb, index, amount);
        backpackUpdateNotifier.notify(player);

        // 推送
        String notify = tradeEncoder.encode(trade);
        trade.getTradePlayerMap().values().forEach(p -> p.getPlayer().syncClient(notify));
    }


    private Integer findEmptyIndex(Map<Integer, ItemDb> items) {
        for (int i = 1; i <= MAX_CAPACITY; i++) {
            if (!items.containsKey(i)) {
                return i;
            }
        }
        return -1;
    }
}
