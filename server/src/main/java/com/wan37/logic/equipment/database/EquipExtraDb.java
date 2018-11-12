package com.wan37.logic.equipment.database;

import java.util.List;

public class EquipExtraDb {

    /**
     * 耐久度
     */
    private double durabilityv;

    private List<EquipAttrDb> baseAttrs;

    public double getDurabilityv() {
        return durabilityv;
    }

    public void setDurabilityv(double durabilityv) {
        this.durabilityv = durabilityv;
    }

    public List<EquipAttrDb> getBaseAttrs() {
        return baseAttrs;
    }

    public void setBaseAttrs(List<EquipAttrDb> baseAttrs) {
        this.baseAttrs = baseAttrs;
    }
}
