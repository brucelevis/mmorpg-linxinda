package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.impl.AttrCfgImpl;

/**
 * 属性配置表实体类
 *
 * @author linda
 */
public class AttrCfgExcel implements ConfigFactory<AttrCfg> {

    private Integer id;
    private String name;
    private String desc;
    private double baseAttackValue;
    private double baseDefenseValue;
    private int equipBaseScore;

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

    public int getEquipBaseScore() {
        return equipBaseScore;
    }

    public void setEquipBaseScore(int equipBaseScore) {
        this.equipBaseScore = equipBaseScore;
    }

    @Override
    public AttrCfg create() {
        return new AttrCfgImpl(this);
    }
}
