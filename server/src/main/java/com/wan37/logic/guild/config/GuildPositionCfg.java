package com.wan37.logic.guild.config;

import java.util.Set;

/**
 * 公会职位配置表
 *
 * @author linda
 */
public interface GuildPositionCfg {

    /**
     * 职位id
     *
     * @return Integer
     */
    Integer getId();

    /**
     * 职位名
     *
     * @return String
     */
    String getName();

    /**
     * 拥有权限
     *
     * @return Set<Integer>
     */
    Set<Integer> getPermission();
}
