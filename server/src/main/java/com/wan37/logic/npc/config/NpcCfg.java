package com.wan37.logic.npc.config;

/**
 * Npc配置表
 *
 * @author linda
 */
public interface NpcCfg {

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
     * 对白
     *
     * @return String
     */
    String getTalk();
}
