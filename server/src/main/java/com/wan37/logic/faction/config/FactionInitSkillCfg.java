package com.wan37.logic.faction.config;

import com.wan37.logic.skill.config.SkillCfg;

/**
 * 门派初始技能配置表
 *
 * @author linda
 */
public interface FactionInitSkillCfg {

    /**
     * 技能id
     *
     * @return Integer
     * @see SkillCfg#getId()
     */
    Integer getSkillCfgId();

    /**
     * 等级
     *
     * @return int
     */
    int getLevel();
}
