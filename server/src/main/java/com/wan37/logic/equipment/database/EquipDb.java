package com.wan37.logic.equipment.database;

import com.wan37.logic.backpack.database.ItemDb;

import java.util.Map;

/**
 * 装备栏
 */
public class EquipDb {

    /**
     * key：partId
     */
    private Map<Integer, ItemDb> items;

    public Map<Integer, ItemDb> getItems() {
        return items;
    }

    public void setItems(Map<Integer, ItemDb> items) {
        this.items = items;
    }
}
