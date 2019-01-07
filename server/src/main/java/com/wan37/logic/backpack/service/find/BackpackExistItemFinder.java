package com.wan37.logic.backpack.service.find;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 返回背包中同种物品的所有格子
 */
@Service
public class BackpackExistItemFinder {

    public List<ItemDb> find(BackpackDb backpackDb, Integer cfgId) {
        return backpackDb.getItemMap().values().stream()
                .filter(i -> Objects.equals(i.getCfgId(), cfgId))
                .collect(Collectors.toList());
    }
}
