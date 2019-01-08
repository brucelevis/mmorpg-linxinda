package com.wan37.logic.skill.config;

import com.wan37.logic.buff.config.BuffCfg;

/**
 * 技能触发buff配置表
 *
 * @author linda
 */
public interface SkillBuffCfg {

    /**
     * buffId
     *
     * @return Integer
     * @see BuffCfg#getId()
     */
    Integer getId();

    /**
     * 触发概率
     *
     * @return double
     */
    double getProbability();
}
