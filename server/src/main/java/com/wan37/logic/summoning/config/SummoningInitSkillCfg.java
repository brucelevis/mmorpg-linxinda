package com.wan37.logic.summoning.config;

import com.wan37.logic.skill.config.SkillCfg;

/**
 * 召唤兽初始技能配置表
 *
 * @author linda
 */
public interface SummoningInitSkillCfg {

    /**
     * 技能id
     *
     * @return Integer
     * @see SkillCfg#getId()
     */
    Integer getId();

    /**
     * 等级
     *
     * @return int
     */
    int getLevel();
}
