package com.wan37.logic.trade.service.add.item;

import com.wan37.logic.backpack.BackpackFacade;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.encode.TradeEncoder;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.entity.Trade;
import com.wan37.logic.trade.entity.TradePlayer;
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

            TradePlayer me = trade.getTradePlayerMap().get(player.getUid());
            if (me.getItems().values().size() >= MAX_CAPACITY) {
                player.syncClient("交易框已满");
                return;
            }

            // 复制物品进交易栏
            ItemDb tradeItem = new ItemDb();
            BeanUtils.copyProperties(itemDb, tradeItem);
            tradeItem.setAmount(amount);
            Integer newIndex = findEmptyIndex(me.getItems());
            tradeItem.setIndex(newIndex);
            me.getItems().put(newIndex, tradeItem);

            // 扣除背包物品
            backpackFacade.remove(backpackDb, index, amount);
            backpackUpdateNotifier.notify(player);

            // 推送
            String notify = tradeEncoder.encode(trade);
            trade.getTradePlayerMap().values().forEach(p -> p.getPlayer().syncClient(notify));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            trade.getLock().unlock();
        }
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
