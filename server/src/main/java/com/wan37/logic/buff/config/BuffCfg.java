package com.wan37.logic.buff.config;

import com.wan37.logic.buff.BuffEffectEnum;
import com.wan37.logic.buff.BuffTargetEnum;

/**
 * Buff配置表
 *
 * @author linda
 */
public interface BuffCfg {

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
     * 效果id
     *
     * @return Integer
     * @see BuffEffectEnum#getId()
     */
    Integer getEffectId();

    /**
     * 描述
     *
     * @return String
     */
    String getDesc();

    /**
     * 是否一次执行
     *
     * @return boolean
     */
    boolean isOnce();

    /**
     * 参数
     *
     * @return String
     */
    String getArg();

    /**
     * 间隔（毫秒）
     *
     * @return int
     */
    int getInterval();

    /**
     * 持续（毫秒）
     *
     * @return int
     */
    int getContinuous();

    /**
     * 作用目标
     *
     * @return Integer
     * @see BuffTargetEnum#getId()
     */
    Integer getTarget();
}
