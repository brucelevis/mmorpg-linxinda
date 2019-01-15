package com.wan37.logic.skill;

import com.wan37.logic.skill.config.SkillCfg;
import com.wan37.logic.skill.database.PlayerEachSkillDb;

/**
 * 技能实例，隔开怪物技能和玩家技能数据库实例
 *
 * @author linda
 */
public interface Skill {

    interface Factory {

        /**
         * 创建怪物技能实例
         *
         * @param skillCfg 技能配置表
         * @param level    技能等级
         * @return Skill
         */
        Skill create(SkillCfg skillCfg, int level);

        /**
         * 创建玩家技能实例，隔开数据库数据
         *
         * @param skillCfg 技能配置表
         * @param skillDb  玩家一个技能数据库实例
         * @return Skill
         */
        Skill create(SkillCfg skillCfg, PlayerEachSkillDb skillDb);
    }

    /**
     * 技能id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 技能名
     *
     * @return String
     */
    String getName();

    /**
     * 技能等级
     *
     * @return int
     */
    int getLevel();

    /**
     * 上次施放时间
     *
     * @return long
     */
    long getLastUseTime();

    /**
     * 设置上次施放时间
     *
     * @param time 上次施放时间
     */
    void setLastUseTime(long time);

    /**
     * 技能cd间隔（毫秒）
     *
     * @return int
     */
    int getCdInterval();

    /**
     * 使用蓝量消耗
     *
     * @return int
     */
    int getCostMp();

    /**
     * 效果值
     *
     * @return double
     */
    double getEffectValue();

    /**
     * 技能配置表
     *
     * @return SkillCfg
     */
    SkillCfg getSkillCfg();
}
