package com.wan37.logic.monster.config;

import java.util.List;

public interface MonsterCfg {

    Integer getId();

    String getName();

    int getCreateCd();

    List<MonsterInitAttrCfg> getAttrs();

    List<MonsterInitSkillCfg> getSkills();

    List<MonsterItemCfg> getItems();

    int getExp();
}
