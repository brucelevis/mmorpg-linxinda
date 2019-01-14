package com.wan37.logic.guild.entity;

import com.wan37.logic.guild.database.GuildGlobalDb;

import java.util.List;

/**
 * 公会仓库接口实例
 *
 * @author linda
 */
public interface GuildWarehouse {

    interface Factory {

        /**
         * 创建公会仓库接口实例
         *
         * @param guildGlobalDb 公会数据库实例
         * @return GuildWarehouse
         */
        GuildWarehouse create(GuildGlobalDb guildGlobalDb);
    }

    /**
     * 添加一种实物
     *
     * @param guildItem 公会格子
     */
    void addItem(GuildItem guildItem);

    /**
     * 删除指定格子物品n个
     *
     * @param index  格子位置
     * @param amount 数量
     */
    void rmItem(Integer index, int amount);

    /**
     * 加一种虚物
     *
     * @param guildCurrency 公会一种虚物接口实例
     */
    void addCurrency(GuildCurrency guildCurrency);

    /**
     * 删除指定虚物
     *
     * @param cfgId  虚物id
     * @param amount 数量
     */
    void rmCurrency(Integer cfgId, long amount);

    /**
     * 查询指定格子物品数量
     *
     * @param index 格子位置
     * @return int
     */
    int queryItem(Integer index);

    /**
     * 返回指定格子
     *
     * @param index 格子位置
     * @return GuildItem
     */
    GuildItem getItem(Integer index);

    /**
     * 查询一种虚物数量
     *
     * @param cfgId 虚物id
     * @return long
     */
    long queryCurrency(Integer cfgId);

    /**
     * 当前仓库容量
     *
     * @return int
     */
    int getCurSize();

    /**
     * 仓库总容量
     *
     * @return int
     */
    int getCapacity();

    /**
     * 返回仓库所有格子
     *
     * @return List<GuildItem>
     */
    List<GuildItem> getItems();

    /**
     * 返回仓库所有虚物
     *
     * @return List<GuildCurrency>
     */
    List<GuildCurrency> getCurrency();
}
