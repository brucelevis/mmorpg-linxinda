package com.wan37.logic.attr.database;

import com.wan37.logic.attr.config.AttrCfg;

/**
 * 人物一条属性
 *
 * @author linda
 */
public class PlayerEachAttrDb {

    /**
     * @see AttrCfg#getId
     */
    private Integer cfgId;

    private double value;

    public Integer getCfgId() {
        return cfgId;
    }

    public void setCfgId(Integer cfgId) {
        this.cfgId = cfgId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
