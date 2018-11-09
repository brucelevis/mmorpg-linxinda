package com.wan37.logic.backpack.encode;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Deprecated
@Service
public class BackpackInfoEncoder {

    public String encode(BackpackDb backpackDb) {
        Map<Integer, ItemDb> items = backpackDb.getItemMap();
        String backpackHead = String.format("背包容量：%s，当前容量：%s，物品信息如下：\n", backpackDb.getCapacity(), items.size());

        String itemStr = items.values().stream()
                .map(this::encodeItem)
                .collect(Collectors.joining());

        return backpackHead + itemStr;
    }

    private String encodeItem(ItemDb itemDb) {
        String msg = "名字：%s，格子：%s，数量：%s （uid：%s，cfgId：%s）\n";
        return String.format(msg, itemDb.getName(), itemDb.getIndex(), itemDb.getAmount(), itemDb.getUid(), itemDb.getCfgId());
    }
}
