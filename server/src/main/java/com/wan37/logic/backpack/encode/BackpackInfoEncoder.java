package com.wan37.logic.backpack.encode;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 背包信息编码
 *
 * @author linda
 */
@Service
public class BackpackInfoEncoder {

    @Autowired
    private BackpackItemSimpleInfoEncoder backpackItemSimpleInfoEncoder;

    public String encode(BackpackDb backpackDb) {
        Map<Integer, ItemDb> items = backpackDb.getItemMap();
        String backpackHead = String.format("背包容量：%s，当前容量：%s，物品信息如下：\n", backpackDb.getCapacity(), items.size());

        String itemStr = items.values().stream()
                .map(i -> backpackItemSimpleInfoEncoder.encode(i))
                .collect(Collectors.joining("\n"));

        return backpackHead + itemStr;
    }
}
