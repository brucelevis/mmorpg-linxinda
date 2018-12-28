package com.wan37.logic.summoning.config;

import java.util.List;

public interface SummoningCfg {

    Integer getId();

    String getName();

    List<SummoningInitAttrCfg> getInitAttrs();

    List<SummoningInitSkillCfg> getInitSkills();
}
