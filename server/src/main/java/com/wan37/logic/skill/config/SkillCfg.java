package com.wan37.logic.skill.config;

import com.wan37.logic.skill.SkillEffectLogicEnum;
import com.wan37.logic.skill.SkillTargetTypeEnum;

import java.util.List;

/**
 * 技能配置表
 *
 * @author linda
 */
public interface SkillCfg {

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
     * 技能描述
     *
     * @return String
     */
    String getDesc();

    /**
     * 冷却（毫秒）
     *
     * @param lv 技能等级
     * @return int
     */
    int getCd(int lv);

    /**
     * 效果值
     *
     * @param lv 技能等级
     * @return double
     */
    double getEffectValue(int lv);

    /**
     * 使用消耗蓝量
     *
     * @param lv 技能等级
     * @return int
     */
    int getCostMp(int lv);

    /**
     * 几率触发Buff配置
     *
     * @return List<SkillBuffCfg>
     */
    List<SkillBuffCfg> getBuffs();

    /**
     * 是否全部作用
     *
     * @return boolean
     */
    boolean isEffectAll();

    /**
     * 效果逻辑id
     *
     * @return Integer
     * @see SkillEffectLogicEnum#getId()
     */
    Integer getEffectLogic();

    /**
     * 作用目标类型
     *
     * @return Integer
     * @see SkillTargetTypeEnum#getId()
     */
    Integer getTargetType();
}
