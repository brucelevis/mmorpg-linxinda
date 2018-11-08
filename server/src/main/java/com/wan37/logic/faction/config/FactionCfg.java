package com.wan37.logic.faction.config;

import java.util.List;

public interface FactionCfg {

    Integer getId();

    String getName();

    List<FactionInitAttrCfg> getInitAttrs();

    List<FactionInitSkillCfg> getInitSkills();
}
