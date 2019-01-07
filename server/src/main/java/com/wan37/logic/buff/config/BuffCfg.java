package com.wan37.logic.buff.config;

import com.wan37.logic.buff.BuffEffectEnum;
import com.wan37.logic.buff.BuffTargetEnum;

/**
 * Buff配置表
 */
public interface BuffCfg {

    Integer getId();

    String getName();

    /**
     * 效果id
     *
     * @see BuffEffectEnum#getId()
     */
    Integer getEffectId();

    String getDesc();

    /**
     * 是否一次执行
     */
    boolean isOnce();

    /**
     * 参数
     */
    String getArg();

    /**
     * 间隔（毫秒）
     */
    int getInterval();

    /**
     * 持续（毫秒）
     */
    int getContinuous();

    /**
     * 作用目标
     *
     * @see BuffTargetEnum#getId()
     */
    Integer getTatget();
}
