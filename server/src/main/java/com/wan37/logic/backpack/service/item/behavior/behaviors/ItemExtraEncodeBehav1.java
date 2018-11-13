package com.wan37.logic.backpack.service.item.behavior.behaviors;

import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavContext;
import com.wan37.logic.backpack.service.item.behavior.ItemExtraEncodeBehavior;
import com.wan37.logic.equipment.database.EquipAttrDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 1：装备
 */
@Service
class ItemExtraEncodeBehav1 implements ItemExtraEncodeBehavior {

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    @Override
    public void behave(ItemExtraEncodeBehavContext context) {
        EquipExtraDb equipExtraDb = equipExtraDbGetter.get(context.getExtraDb());


        String durability = String.format("耐久度：%s，", equipExtraDb.getDurabilityv());
        String msg = "装备属性信息：" + encodeExtra(equipExtraDb);
        context.setResult(durability + msg);
    }

    private String encodeExtra(EquipExtraDb db) {
        return db.getBaseAttrs().stream()
                .map(this::encodeAttr)
                .collect(Collectors.joining("，"));
    }

    private String encodeAttr(EquipAttrDb db) {
        String msg = "%s：%s";
        return String.format(msg, db.getName(), db.getValue());
    }
}
