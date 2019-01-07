package com.wan37.logic.equipment.database;

import java.util.List;

/**
 * 装备额外信息数据库实体类
 *
 * @author linda
 */
public class EquipExtraDb {

    /**
     * 耐久度
     */
    private double durability;

    private List<EquipAttrDb> baseAttrs;

    public double getDurability() {
        return durability;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    public List<EquipAttrDb> getBaseAttrs() {
        return baseAttrs;
    }

    public void setBaseAttrs(List<EquipAttrDb> baseAttrs) {
        this.baseAttrs = baseAttrs;
    }
}
