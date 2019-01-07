package com.wan37.logic.props.resource.add;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.ItemAddEvent;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.service.find.BackpackEmptyIndexFinder;
import com.wan37.logic.backpack.service.find.BackpackExistItemFinder;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.init.PropsExtraInitializer;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 添加实物
 *
 * @author linda
 */
@Service
public class ResourceItemAdder {

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private BackpackEmptyIndexFinder backpackEmptyIndexFinder;

    @Autowired
    private PropsExtraInitializer propsExtraInitializer;

    @Autowired
    private BackpackExistItemFinder backpackExistItemFinder;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public boolean add(ResourceElement element, Player player) {
        PropsCfg propsCfg = propsCfgLoader.load(element.getCfgId()).orElse(null);
        if (propsCfg == null) {
            return false;
        }

        //TODO: 是否直接消耗使用的物品

        long amount = element.getAmount();
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        if (!canAdd(propsCfg, amount, backpackDb)) {
            player.syncClient("背包已满");
            return false;
        }

        int maxOverlay = propsCfg.getMaxOverLay();
        if (canOverlay(propsCfg)) {
            // 先找到已经存在的物品，并且物品叠加上限没满
            List<ItemDb> existItems = backpackExistItemFinder.find(backpackDb, propsCfg.getId()).stream()
                    .filter(i -> i.getAmount() < propsCfg.getMaxOverLay())
                    .collect(Collectors.toList());

            // 枚举一个个已存在物品塞满
            for (int i = 0; i < existItems.size() && amount > 0; i++) {
                ItemDb itemDb = existItems.get(i);
                int remainAmount = maxOverlay - itemDb.getAmount();

                if (amount <= remainAmount) {
                    // 可叠加用完
                    itemDb.setAmount(itemDb.getAmount() + (int) amount);
                    amount = 0;
                } else {
                    // 不可叠加用完
                    itemDb.setAmount(propsCfg.getMaxOverLay());
                    amount -= remainAmount;
                }

                // 标记背包格子更新
                backpackDb.getIndex().add(itemDb.getIndex());
            }

            // 还有开始找格子创建
            while (amount > 0) {
                // 找到空的格子索引
                Integer index = backpackEmptyIndexFinder.find(backpackDb);
                ItemDb itemDb;
                if (amount >= maxOverlay) {
                    itemDb = createItem(propsCfg, index, maxOverlay);
                    amount -= maxOverlay;
                } else {
                    itemDb = createItem(propsCfg, index, (int) amount);
                    amount = 0;
                }

                backpackDb.getIndex().add(index);
                backpackDb.getItemMap().put(index, itemDb);
            }
        } else {
            // 不可叠加
            while (amount > 0) {
                // 找到空的格子索引
                Integer index = backpackEmptyIndexFinder.find(backpackDb);
                ItemDb itemDb = createItem(propsCfg, index, 1);

                // 初始化额外信息
                Object extraDb = propsExtraInitializer.init(propsCfg);
                itemDb.setExtraDb(extraDb);

                backpackDb.getIndex().add(index);
                backpackDb.getItemMap().put(index, itemDb);
                amount--;
            }
        }

        // 抛出加物品事件
        generalEventListenersManager.fireEvent(new ItemAddEvent(propsCfg, (int) amount, player));

        return true;
    }

    private ItemDb createItem(PropsCfg propsCfg, Integer index, int amount) {
        ItemDb itemDb = new ItemDb();

        itemDb.setUid(IdUtil.generate());
        itemDb.setCfgId(propsCfg.getId());
        itemDb.setAmount(amount);
        itemDb.setIndex(index);

        return itemDb;
    }

    private boolean canAdd(PropsCfg propsCfg, long amount, BackpackDb backpackDb) {
        int maxOverlay = propsCfg.getMaxOverLay();
        int capacity = backpackDb.getCapacity();

        Map<Integer, ItemDb> itemMap = backpackDb.getItemMap();
        int currentCapacity = itemMap.values().size();

        if (!canOverlay(propsCfg)) {
            // 不可叠加
            return capacity - currentCapacity > 0;
        }

        // 找到所有已存在的物品
        List<ItemDb> existSameItems = backpackExistItemFinder.find(backpackDb, propsCfg.getId());

        // 还可以加多少数量
        long remainAmount = (capacity - currentCapacity) * maxOverlay
                + existSameItems.stream()
                .mapToLong(i -> maxOverlay - i.getAmount())
                .sum();

        return remainAmount - amount >= 0;
    }

    private boolean canOverlay(PropsCfg propsCfg) {
        return propsCfg.getMaxOverLay() > 1;
    }
}
