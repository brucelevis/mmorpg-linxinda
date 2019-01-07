package com.wan37.logic.buff.entity;

import com.wan37.logic.buff.BuffTargetEnum;
import com.wan37.logic.buff.config.BuffCfg;

/**
 * Buff统一接口
 *
 * @author linda
 */
public interface Buff {

    interface Factory {

        /**
         * 创建Buff
         *
         * @param buffCfg Buff配置表
         * @return Buff
         */
        Buff create(BuffCfg buffCfg);
    }

    /**
     * 唯一id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 效果id
     *
     * @return Integer
     */
    Integer getEffectId();

    /**
     * 是否一次执行
     *
     * @return boolean
     */
    boolean isOnce();

    /**
     * 名字
     *
     * @return String
     */
    String getName();

    /**
     * 过期时间
     *
     * @return long
     */
    long getExpireTime();

    /**
     * 上次生效时间
     *
     * @return long
     */
    long getLastEffectTime();

    /**
     * 设置上次生效时间
     *
     * @param time 时间
     */
    void setLastEffectTime(long time);

    /**
     * 间隔（毫秒）
     *
     * @return int
     */
    int getInterval();

    /**
     * 参数
     *
     * @return String
     */
    String getArg();

    /**
     * 持续时间（毫秒）
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
