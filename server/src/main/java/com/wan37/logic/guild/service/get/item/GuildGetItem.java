package com.wan37.logic.guild.service.get.item;

/**
 * 要取公会仓库格子物品及数量
 *
 * @author linda
 */
public interface GuildGetItem {

    /**
     * 公会仓库格子位置
     *
     * @return Integer
     */
    Integer getIndex();

    /**
     * 要取的物品数量
     *
     * @return int
     */
    int getAmount();
}
