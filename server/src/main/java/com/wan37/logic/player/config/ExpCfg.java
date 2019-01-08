package com.wan37.logic.player.config;

/**
 * 玩家等级经验配置表
 *
 * @author linda
 */
public interface ExpCfg {

    /**
     * 等级
     *
     * @return int
     */
    int getId();

    /**
     * 经验
     *
     * @return long
     */
    long getExp();
}
