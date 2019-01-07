package com.wan37.logic.summoning.config;

import java.util.List;

/**
 * 召唤兽配置表
 *
 * @author linda
 */
public interface SummoningCfg {

    /**
     * 唯一id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 名字
     *
     * @return String
     */
    String getName();

    /**
     * 初始属性配置
     *
     * @return List<SummoningInitAttrCfg>
     */
    List<SummoningInitAttrCfg> getInitAttrs();

    /**
     * 初始技能配置
     *
     * @return List<SummoningInitSkillCfg>
     */
    List<SummoningInitSkillCfg> getInitSkills();
}
