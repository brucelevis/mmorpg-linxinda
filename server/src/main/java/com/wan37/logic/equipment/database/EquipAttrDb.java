package com.wan37.logic.equipment.database;

import com.wan37.logic.attr.config.AttrCfg;

public class EquipAttrDb {

    /**
     * @see AttrCfg#getId
     */
    private Integer cfgId;

    private double value;

    /**
     * FIXME: 为了方便前端显示，把属性名存起来
     */
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
