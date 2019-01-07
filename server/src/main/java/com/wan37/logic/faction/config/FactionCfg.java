package com.wan37.logic.faction.config;

import java.util.List;

/**
 * 门派职业配置表
 *
 * @author linda
 */
public interface FactionCfg {

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
     * 门派初始属性配置
     *
     * @return List<FactionInitAttrCfg>
     */
    List<FactionInitAttrCfg> getInitAttrs();

    /**
     * 门派初始技能配置
     *
     * @return List<FactionInitSkillCfg>
     */
    List<FactionInitSkillCfg> getInitSkills();
}
