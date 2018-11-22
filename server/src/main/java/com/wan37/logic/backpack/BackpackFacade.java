package com.wan37.logic.backpack;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.backpack.service.find.BackpackEmptyIndexFinder;
import com.wan37.logic.player.Player;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class BackpackFacade {

    private static final Logger LOG = Logger.getLogger(BackpackFacade.class);

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private BackpackEmptyIndexFinder backpackEmptyIndexFinder;

    public Optional<ItemDb> find(BackpackDb backpackDb, Long uid) {
        return backpackDb.getItemMap().values().stream()
                .filter(i -> Objects.equals(i.getUid(), uid))
                .findAny();
    }

    public Optional<ItemDb> find(BackpackDb backpackDb, Integer index) {
        ItemDb itemDb = backpackDb.getItemMap().get(index);
        if (itemDb == null) {
            return Optional.empty();
        }

        return Optional.of(itemDb);
    }

    public void remove(Player player, Long uid) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        ItemDb itemDb = find(backpackDb, uid).orElse(null);
        if (itemDb == null) {
            return;
        }

        Integer index = itemDb.getIndex();
        backpackDb.getItemMap().remove(index);

        // 标记背包格子更新
        backpackDb.getIndexs().add(index);

        backpackUpdateNotifier.notify(player);
    }

    public void remove(Player player, Integer index, int amount) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        ItemDb itemDb = backpackDb.getItemMap().get(index);
        if (itemDb == null) {
            LOG.info("找不到要删除的背包物品");
            return;
        }

        int currentAmount = itemDb.getAmount();
        if (currentAmount < amount) {
            LOG.info("要删除的背包物品数量不足");
            return;
        }

        itemDb.setAmount(currentAmount - amount);
        if (itemDb.getAmount() <= 0) {
            // 数量为0，移除
            backpackDb.getItemMap().remove(index);
        } else {
            backpackDb.getItemMap().put(index, itemDb);
        }

        // 标记背包格子更新
        backpackDb.getIndexs().add(index);
    }

    public void add(Player player, ItemDb itemDb) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        Integer index = backpackEmptyIndexFinder.find(backpackDb);

        itemDb.setIndex(index);
        backpackDb.getItemMap().put(index, itemDb);

        // 标记背包格子更新
        backpackDb.getIndexs().add(index);

        backpackUpdateNotifier.notify(player);
    }
}
