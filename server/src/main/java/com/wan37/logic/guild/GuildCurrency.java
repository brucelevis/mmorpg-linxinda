package com.wan37.logic.guild;

import com.wan37.logic.guild.database.GuildCurrencyDb;
import com.wan37.logic.props.config.VirtualItemCfg;

/**
 * 公会仓库一种虚物
 *
 * @author linda
 */
public interface GuildCurrency {

    interface Factory {

        /**
         * 创建公会仓库一种虚物
         *
         * @param currencyDb 公会钱库数据库实例
         * @return GuildCurrency
         */
        GuildCurrency create(GuildCurrencyDb currencyDb);
    }

    /**
     * 公会钱库数据库实例
     *
     * @return GuildCurrencyDb
     */
    GuildCurrencyDb getGuildCurrencyDb();

    /**
     * 虚物id
     *
     * @return Integer
     * @see VirtualItemCfg#getId()
     */
    Integer getCfgId();

    /**
     * 数量
     *
     * @return long
     */
    long getAmount();

    /**
     * 设置数量
     *
     * @param amount 数量
     */
    void setAmount(long amount);
}
