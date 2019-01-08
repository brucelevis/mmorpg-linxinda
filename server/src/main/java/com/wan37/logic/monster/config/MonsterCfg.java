package com.wan37.logic.monster.config;

import java.util.List;

/**
 * 怪物配置表
 *
 * @author linda
 */
public interface MonsterCfg {

    /**
     * 怪物id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 怪物名
     *
     * @return String
     */
    String getName();

    /**
     * 怪物生成cd（毫秒）
     *
     * @return int
     */
    int getCreateCd();

    /**
     * 怪物初始属性配置
     *
     * @return List<MonsterInitAttrCfg>
     */
    List<MonsterInitAttrCfg> getAttrs();

    /**
     * 怪物初始技能配置
     *
     * @return List<MonsterInitSkillCfg>
     */
    List<MonsterInitSkillCfg> getSkills();

    /**
     * 怪物掉落配置
     *
     * @return List<MonsterItemCfg>
     */
    List<MonsterItemCfg> getItems();

    /**
     * 经验奖励
     *
     * @return int
     */
    int getExp();
}
