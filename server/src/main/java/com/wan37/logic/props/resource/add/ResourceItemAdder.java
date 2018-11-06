package com.wan37.logic.props.resource.add;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.util.IdTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResourceItemAdder {

    private static final Logger LOG = Logger.getLogger(ResourceItemAdder.class);

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private IdTool idTool;

    public void add(ResourceElement element, BackpackDb backpackDb) {
        PropsCfg propsCfg = propsCfgLoader.load(element.getCfgId()).orElse(null);
        if (propsCfg == null) {
            return;
        }

        //TODO: 是否直接消耗使用的物品

        long amount = element.getAmount();
        if (!canAdd(propsCfg, amount, backpackDb)) {
            LOG.info("背包剩余空间不足！");
            return;
        }

        if (canOverlay(propsCfg)) {
            //TODO: 可叠加
        } else {
            // 不可叠加
            while (amount > 0) {
                // 找到空的格子索引
                Integer index = findEmptyIndex(backpackDb);
                ItemDb itemDb = createUniqueItem(propsCfg, index);

                backpackDb.getIndexs().add(index);
                backpackDb.getItemMap().put(index, itemDb);
                amount--;
            }
        }
    }

    private Integer findEmptyIndex(BackpackDb backpackDb) {
        Map<Integer, ItemDb> itemMap = backpackDb.getItemMap();
        for (int i = 1; i <= backpackDb.getCapacity(); i++) {
            if (!itemMap.containsKey(i)) {
                return i;
            }
        }
        return -1;
    }

    private ItemDb createUniqueItem(PropsCfg propsCfg, Integer index) {
        ItemDb itemDb = new ItemDb();

        itemDb.setUid(idTool.generate());
        itemDb.setCfgId(propsCfg.getId());
        itemDb.setAmount(1);
        itemDb.setName(propsCfg.getName());
        itemDb.setIndex(index);

        //TODO: 初始化额外信息

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
        List<ItemDb> existSameItems = findExistItems(propsCfg.getId(), backpackDb);

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

    private List<ItemDb> findExistItems(Integer cfgId, BackpackDb backpackDb) {
        return backpackDb.getItemMap().values().stream()
                .filter(i -> Objects.equals(i.getCfgId(), cfgId))
                .collect(Collectors.toList());
    }
}
