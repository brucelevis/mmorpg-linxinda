package com.wan37.logic.backpack.service;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.encode.BackpackInfoEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 背包整理
 */
@Service
public class BackpackCleanExec {

    @Autowired
    private BackpackInfoEncoder backpackInfoEncoder;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    public void exec(Player player) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();

        Set<Map.Entry<Integer, List<ItemDb>>> entries = backpackDb.getItemMap().values().stream()
                .collect(Collectors.groupingBy(ItemDb::getCfgId))
                .entrySet();

        int index = 1;
        Map<Integer, ItemDb> backpack = new HashMap<>();
        for (Map.Entry<Integer, List<ItemDb>> entry : entries) {
            PropsCfg propsCfg = propsCfgLoader.load(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("物品导表找不到对应物品"));

            List<ItemDb> items = entry.getValue();
            int maxOverLay = propsCfg.getMaxOverLay();
            if (maxOverLay > 1) {
                // 可堆叠
                long total = items.stream()
                        .mapToLong(ItemDb::getAmount)
                        .sum();

                for (ItemDb itemDb : items) {
                    itemDb.setIndex(index);
                    backpack.put(index, itemDb);
                    if (total > maxOverLay) {
                        itemDb.setAmount(maxOverLay);

                        index++;
                        total -= maxOverLay;
                    } else {
                        itemDb.setAmount((int) total);

                        index++;
                        break;
                    }
                }
            } else {
                // 不可堆叠
                for (ItemDb itemDb : items) {
                    itemDb.setIndex(index);
                    backpack.put(index, itemDb);
                    index++;
                }
            }
        }

        backpackDb.setItemMap(backpack);

        String update = backpackInfoEncoder.encode(backpackDb);
        player.syncClient(update);
    }
}
