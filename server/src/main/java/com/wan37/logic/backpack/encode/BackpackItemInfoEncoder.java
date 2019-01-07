package com.wan37.logic.backpack.encode;

import com.wan37.behavior.BehaviorManager;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehaviorContext;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavior;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 背包单个物品详细信息编码
 *
 * @author linda
 */
@Service
public class BackpackItemInfoEncoder {

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    @Autowired
    private BehaviorManager behaviorManager;

    public String encode(ItemDb itemDb) {
        PropsCfg propsCfg = propsCfgLoader.load(itemDb.getCfgId()).orElse(null);
        if (propsCfg == null) {
            return "";
        }

        String baseMsg = encodeBaseMsg(itemDb, propsCfg);

        Integer type = propsCfg.getType();
        ItemExtraEncodeBehavior behavior = (ItemExtraEncodeBehavior) behaviorManager.get(ItemExtraEncodeBehavior.class, type);

        ItemExtraEncodeBehaviorContext ctx = new ItemExtraEncodeBehaviorContext(itemDb.getCfgId(), itemDb.getExtraDb());
        behavior.behave(ctx);

        return baseMsg + ctx.getResult();
    }

    private String encodeBaseMsg(ItemDb itemDb, PropsCfg propsCfg) {
        String msg = "名字：%s，格子：%s, 数量：%s，描述：%s，最大堆叠：%s，能否使用: %s \n";
        return String.format(msg, propsCfg.getName(), itemDb.getIndex(), itemDb.getAmount(), propsCfg.getDesc(), propsCfg.getMaxOverLay(), propsCfg.isCanUse());
    }
}
