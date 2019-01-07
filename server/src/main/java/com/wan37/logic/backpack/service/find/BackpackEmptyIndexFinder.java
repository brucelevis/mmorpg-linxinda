package com.wan37.logic.backpack.service.find;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 返回背包的一个空格子，默认是返回最小编号，背包满则返回-1
 */
@Service
public class BackpackEmptyIndexFinder {

    public Integer find(BackpackDb backpackDb) {
        Map<Integer, ItemDb> itemMap = backpackDb.getItemMap();
        for (int i = 1; i <= backpackDb.getCapacity(); i++) {
            if (!itemMap.containsKey(i)) {
                return i;
            }
        }
        return -1;
    }
}
