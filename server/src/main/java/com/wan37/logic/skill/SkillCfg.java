package com.wan37.logic.skill;

public interface SkillCfg {

    Integer getId();

    String getName();

    String getDesc();

    int getCd(int lv);

    int getMaxLevel();
}
