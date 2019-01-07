package com.wan37.logic.equipment.config;

import com.wan37.logic.equipment.EquipPartEnum;

import java.util.List;

/**
 * 装备配置表
 *
 * @author linxinda
 */
public interface EquipCfg {

    /**
     * 唯一id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 部位id
     *
     * @return Integer
     * @see EquipPartEnum#getId()
     */
    Integer getPart();

    /**
     * 装备初始属性配置
     *
     * @return List<EquipInitAttrCfg>
     */
    List<EquipInitAttrCfg> getAttrs();

    /**
     * 佩戴等级
     *
     * @return int
     */
    int getLevel();

    /**
     * 装备品质配置
     *
     * @return List<EquipQualityCfg>
     */
    List<EquipQualityCfg> getQuality();
}
