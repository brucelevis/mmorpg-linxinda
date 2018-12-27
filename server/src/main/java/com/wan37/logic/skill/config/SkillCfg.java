package com.wan37.logic.skill.config;

import java.util.List;

public interface SkillCfg {

    Integer getId();

    String getName();

    String getDesc();

    int getCd(int lv);

    int getMaxLevel();

    double getEffectValue(int lv);

    int getCostMp(int lv);

    List<SkillBuffCfg> getBuffs();

    boolean isEffectAll();

    Integer getEffectLogic();

    Integer getTargetType();
}
