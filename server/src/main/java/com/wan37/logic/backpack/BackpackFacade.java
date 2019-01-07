package com.wan37.logic.backpack;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackUpdateNotifier;
import com.wan37.logic.backpack.service.find.BackpackEmptyIndexFinder;
import com.wan37.logic.backpack.service.find.BackpackExistItemFinder;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 门面模式：背包操作统一入口
 *
 * @author linda
 */
@Service
public class BackpackFacade {

    private static final Logger LOG = Logger.getLogger(BackpackFacade.class);

    @Autowired
    private BackpackUpdateNotifier backpackUpdateNotifier;

    @Autowired
    private BackpackEmptyIndexFinder backpackEmptyIndexFinder;

    @Autowired
    private BackpackExistItemFinder backpackExistItemFinder;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

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
        backpackDb.getIndex().add(index);

        backpackUpdateNotifier.notify(player);
    }

    public void remove(BackpackDb backpackDb, Integer index, int amount) {
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
        backpackDb.getIndex().add(index);
    }

    public void add(BackpackDb backpackDb, ItemDb itemDb) {
        PropsCfg propsCfg = propsCfgLoader.load(itemDb.getCfgId()).orElse(null);
        if (propsCfg == null) {
            return;
        }

        if (propsCfg.getMaxOverLay() > 1) {
            // 可堆叠，先找到已经存在的物品，并且物品叠加上限没满
            List<ItemDb> existItems = backpackExistItemFinder.find(backpackDb, propsCfg.getId()).stream()
                    .filter(i -> i.getAmount() < propsCfg.getMaxOverLay())
                    .collect(Collectors.toList());

            // 枚举一个个已存在物品塞满
            for (int i = 0; i < existItems.size() && itemDb.getAmount() > 0; i++) {
                ItemDb item = existItems.get(i);
                int remainAmount = propsCfg.getMaxOverLay() - item.getAmount();

                if (itemDb.getAmount() <= remainAmount) {
                    // 可叠加用完
                    item.setAmount(item.getAmount() + itemDb.getAmount());
                    itemDb.setAmount(0);
                } else {
                    // 不可叠加用完
                    item.setAmount(propsCfg.getMaxOverLay());
                    itemDb.setAmount(itemDb.getAmount() - remainAmount);
                }

                // 标记背包格子更新
                backpackDb.getIndex().add(item.getIndex());
            }
        }

        if (itemDb.getAmount() > 0) {
            Integer index = backpackEmptyIndexFinder.find(backpackDb);
            itemDb.setIndex(index);
            backpackDb.getItemMap().put(index, itemDb);

            // 标记背包格子更新
            backpackDb.getIndex().add(index);
        }
    }

    public void add(Player player, List<ItemDb> items) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();

        items.forEach(i -> add(backpackDb, i));
        backpackUpdateNotifier.notify(player);
    }

    public int getSpareCapacity(Player player) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        return backpackDb.getCapacity() - backpackDb.getItemMap().size();
    }
}
