package com.wan37.logic.attack.fighting;

public interface FightingSkill {

    Integer getId();

    long getLastUseTime();

    int getCostMp();

    double getDemageAddition();
}
