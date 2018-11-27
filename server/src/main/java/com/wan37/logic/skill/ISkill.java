package com.wan37.logic.skill;

import com.wan37.logic.skill.config.SkillCfg;

public interface ISkill {

    interface Factory {

        ISkill create(SkillCfg skillCfg, int level);
    }

    Integer getId();

    String getName();

    int getLevel();

    long getLastUseTime();

    void setLastUseTime(long time);

    int getCdInterval();

    int getCostMp();

    double getDemageAddition();
}
