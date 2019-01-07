package com.wan37.logic.backpack.database;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 人物背包数据库实体类
 */
public class BackpackDb {

    /**
     * key: index格子索引
     */
    private Map<Integer, ItemDb> itemMap;

    /**
     * 背包容量
     */
    private int capacity;

    /**
     * 保存背包格子需要推送更新的索引
     */
    @JSONField(serialize = false)
    private Set<Integer> indexs = new HashSet<>();

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

    public Set<Integer> getIndexs() {
        return indexs;
    }

    public void setIndexs(Set<Integer> indexs) {
        this.indexs = indexs;
    }
}
