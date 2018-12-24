package com.wan37.logic.equipment.config;

import java.util.List;

public interface EquipCfg {

    Integer getId();

    Integer getPart();

    List<EquipInitAttrCfg> getAttrs();

    int getLevel();

    List<EquipQualityCfg> getQuality();
}
