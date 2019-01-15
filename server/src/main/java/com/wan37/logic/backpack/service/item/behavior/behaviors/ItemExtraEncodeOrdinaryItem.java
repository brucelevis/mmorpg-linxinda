package com.wan37.logic.backpack.service.item.behavior.behaviors;

import com.wan37.behavior.BehaviorLogic;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehaviorContext;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavior;
import org.springframework.stereotype.Service;

/**
 * 2：普通物品
 */
@Service
@BehaviorLogic(id = 2)
class ItemExtraEncodeOrdinaryItem implements ItemExtraEncodeBehavior {

    @Override
    public void behave(ItemExtraEncodeBehaviorContext context) {
        context.setResult("");
    }
}
