package com.wan37.logic.attr.database;

import java.util.Map;

/**
 * 人物基础属性
 */
public class PlayerAttrDb {

    private Map<Integer, PAttrDb> attrs;

    public Map<Integer, PAttrDb> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<Integer, PAttrDb> attrs) {
        this.attrs = attrs;
    }
}
