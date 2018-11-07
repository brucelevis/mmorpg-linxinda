package com.wan37.logic.equipment.database;

import com.alibaba.fastjson.annotation.JSONField;
import com.wan37.logic.backpack.database.ItemDb;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 装备栏
 */
public class EquipDb {

    /**
     * key：partId
     */
    private Map<Integer, ItemDb> items;

    /**
     * 装备穿脱更新标记
     */
    @JSONField(serialize = false)
    private Set<Integer> parts = new HashSet<>();

    public Map<Integer, ItemDb> getItems() {
        return items;
    }

    public void setItems(Map<Integer, ItemDb> items) {
        this.items = items;
    }

    public Set<Integer> getParts() {
        return parts;
    }

    public void setParts(Set<Integer> parts) {
        this.parts = parts;
    }
}
