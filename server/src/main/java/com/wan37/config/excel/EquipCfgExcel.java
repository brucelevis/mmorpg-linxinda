package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.equipment.config.EquipCfg;
import com.wan37.logic.equipment.config.impl.EquipCfgImpl;

/**
 * 装备配置表实体类
 *
 * @author linda
 */
public class EquipCfgExcel implements ConfigFactory<EquipCfg> {

    private Integer id;
    private String attr;
    private Integer part;
    private int level;
    private String quality;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public EquipCfg create() {
        return new EquipCfgImpl(this);
    }
}
