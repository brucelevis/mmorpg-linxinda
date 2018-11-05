package com.wan37.logic.backpack.database;

import java.util.Map;

public class BackpackDb {

    private Map<Integer, ItemDb> itemMap;

    private int capacity;

    public Map<Integer, ItemDb> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Integer, ItemDb> itemMap) {
        this.itemMap = itemMap;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
