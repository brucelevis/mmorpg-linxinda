package com.wan37.logic.skill.config;

public interface SkillCfg {

    Integer getId();

    String getName();

    String getDesc();

    int getCd(int lv);

    int getMaxLevel();

    double getDemage(int lv);
}
