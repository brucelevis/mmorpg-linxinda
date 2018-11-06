package com.wan37.logic.attr.database;

import java.util.Map;

public class PlayerAttrDb {

    private Map<Integer, PAttrDb> attrs;

    public Map<Integer, PAttrDb> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<Integer, PAttrDb> attrs) {
        this.attrs = attrs;
    }
}
