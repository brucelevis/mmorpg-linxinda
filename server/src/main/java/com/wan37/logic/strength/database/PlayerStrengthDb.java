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
    private long baseAttackVal;

    /**
     * 面板基础总防
     */
    private long baseDefenseVal;

    public Map<Integer, Double> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<Integer, Double> attrs) {
        this.attrs = attrs;
    }

    public long getBaseAttackVal() {
        return baseAttackVal;
    }

    public void setBaseAttackVal(long baseAttackVal) {
        this.baseAttackVal = baseAttackVal;
    }

    public long getBaseDefenseVal() {
        return baseDefenseVal;
    }

    public void setBaseDefenseVal(long baseDefenseVal) {
        this.baseDefenseVal = baseDefenseVal;
    }
}
