package com.wan37.logic.strength.database;

import com.wan37.logic.attr.config.AttrCfg;

import java.util.Map;

public class PlayerStrengthDb {

    /**
     * key：
     *
     * @see AttrCfg#getId
     */
    private Map<Integer, Double> attrs;

    /**
     * 面板基础总伤
     */
    private long baseVal;

    public Map<Integer, Double> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<Integer, Double> attrs) {
        this.attrs = attrs;
    }

    public long getBaseVal() {
        return baseVal;
    }

    public void setBaseVal(long baseVal) {
        this.baseVal = baseVal;
    }
}
