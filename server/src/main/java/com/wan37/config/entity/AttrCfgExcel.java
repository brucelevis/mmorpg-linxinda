package com.wan37.config.entity;

public class AttrCfgExcel {

    private Integer id;
    private String name;
    private String desc;
    private double baseAttackValue;
    private double baseDefenseValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getBaseAttackValue() {
        return baseAttackValue;
    }

    public void setBaseAttackValue(double baseAttackValue) {
        this.baseAttackValue = baseAttackValue;
    }

    public double getBaseDefenseValue() {
        return baseDefenseValue;
    }

    public void setBaseDefenseValue(double baseDefenseValue) {
        this.baseDefenseValue = baseDefenseValue;
    }
}
