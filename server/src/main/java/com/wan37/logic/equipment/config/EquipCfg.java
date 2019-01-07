package com.wan37.logic.equipment.config;

import com.wan37.logic.equipment.EquipPartEnum;

import java.util.List;

/**
 * 装备配置表
 *
 * @author linxinda
 */
public interface EquipCfg {

    Integer getId();

    /**
     * @Retrun Integer
     * @see EquipPartEnum#getId()
     */
    Integer getPart();

    /**
     * hehe
     *
     * @return List<EquipInitAttrCfg>
     */
    List<EquipInitAttrCfg> getAttrs();

    /**
     *
     */
    int getLevel();

    List<EquipQualityCfg> getQuality();
}
