package com.wan37.logic.backpack.service.find;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import org.springframework.stereotype.Service;

import java.util.Map;

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
