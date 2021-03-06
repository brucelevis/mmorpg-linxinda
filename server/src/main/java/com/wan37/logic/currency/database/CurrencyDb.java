package com.wan37.logic.currency.database;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 玩家虚物数据库实体类
 *
 * @author linda
 */
public class CurrencyDb {

    /**
     * key：VirtualItemCfg#getId
     */
    private Map<Integer, CurrencyItemDb> itemMap;

    /**
     * 保存更新的虚拟物品id
     */
    @JSONField(serialize = false)
    private Set<Integer> ids = new HashSet<>();

    public Map<Integer, CurrencyItemDb> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Integer, CurrencyItemDb> itemMap) {
        this.itemMap = itemMap;
    }

    public Set<Integer> getIds() {
        return ids;
    }
}
