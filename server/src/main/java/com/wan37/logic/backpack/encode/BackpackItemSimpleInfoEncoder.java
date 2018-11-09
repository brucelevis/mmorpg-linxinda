package com.wan37.logic.backpack.encode;

import com.wan37.logic.backpack.database.ItemDb;
import org.springframework.stereotype.Service;

@Deprecated
@Service
public class BackpackItemSimpleInfoEncoder {

    public String encode(ItemDb itemDb) {
        String msg = "名字：%s，格子：%s，数量：%s （uid：%s，cfgId：%s）\n";
        return String.format(msg, itemDb.getName(), itemDb.getIndex(), itemDb.getAmount(), itemDb.getUid(), itemDb.getCfgId());
    }
}
