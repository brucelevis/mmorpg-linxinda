package com.wan37.logic.backpack.service.item.behavior.behaviors;

import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavContext;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavior;
import org.springframework.stereotype.Service;

/**
 * 2：普通物品
 */
@Service
class ItemExtraEncodeBehav2 implements ItemExtraEncodeBehavior {

    @Override
    public void behave(ItemExtraEncodeBehavContext context) {
        context.setResult("");
    }
}
