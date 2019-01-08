package com.wan37.logic.monster.config;

import com.wan37.logic.skill.config.SkillCfg;

/**
 * 怪物初始技能配置
 *
 * @author linda
 */
public interface MonsterInitSkillCfg {

    /**
     * 技能id
     *
     * @return Integer
     * @see SkillCfg#getId()
     */
    Integer getId();

    /**
     * 技能等级
     *
     * @return int
     */
    int getLevel();
}
