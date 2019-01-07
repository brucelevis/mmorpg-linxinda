package com.wan37.logic.attr.database;

import java.util.Map;

/**
 * 人物基础属性
 *
 * @author linda
 */
public class PlayerAttrDb {

    private Map<Integer, PlayerEachAttrDb> attrs;

    public Map<Integer, PlayerEachAttrDb> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<Integer, PlayerEachAttrDb> attrs) {
        this.attrs = attrs;
    }
}
