package com.wan37.logic.backpack.encode;

import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 背包单个物品简单信息编码
 *
 * @author linda
 */
@Service
public class BackpackItemSimpleInfoEncoder {

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    public String encode(ItemDb itemDb) {
        String msg = "格子：%s，名字：%s，数量：%s （uid：%s，cfgId：%s）";
        return String.format(msg, itemDb.getIndex(), propsCfgLoader.getName(itemDb.getCfgId()), itemDb.getAmount(), itemDb.getUid(), itemDb.getCfgId());
    }
}
