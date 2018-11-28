package com.wan37.logic.skill;

import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.database.PSkillDb;

public interface ISkill {

    interface Factory {

        ISkill create(SkillCfg skillCfg, int level);

        ISkill create(SkillCfg skillCfg, PSkillDb skillDb);
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
