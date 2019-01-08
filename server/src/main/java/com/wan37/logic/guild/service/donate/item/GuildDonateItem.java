package com.wan37.logic.guild.service.donate.item;

/**
 * 仓库捐献背包物品
 *
 * @author linda
 */
public interface GuildDonateItem {

    /**
     * 背包格子位置
     *
     * @return Integer
     */
    Integer getIndex();

    /**
     * 捐献数量
     *
     * @return int
     */
    int getAmount();
}
