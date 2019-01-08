package com.wan37.logic.guild.entity;

import com.wan37.logic.guild.database.GuildItemDb;
import com.wan37.logic.props.config.PropsCfg;

/**
 * 公会仓库格子接口实例
 *
 * @author linda
 */
public interface GuildItem {

    interface Factory {

        /**
         * 创建公会仓库格子接口实例
         *
         * @param itemDb 公会仓库格子数据库实例
         * @return GuildItem
         */
        GuildItem create(GuildItemDb itemDb);
    }

    /**
     * 返回仓库格子数据库实例
     *
     * @return GuildItemDb
     */
    GuildItemDb getGuildItemDb();

    /**
     * 格子位置
     *
     * @return Integer
     */
    Integer getIndex();

    /**
     * 实物id
     *
     * @return Integer
     * @see PropsCfg#getId()
     */
    Integer getCfgId();

    /**
     * 设置格子位置
     *
     * @param index 格子位置
     */
    void setIndex(Integer index);

    /**
     * 数量
     *
     * @return int
     */
    int getAmount();

    /**
     * 设置数量
     *
     * @param amount 数量
     */
    void setAmount(int amount);
}
