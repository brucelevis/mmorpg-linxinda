package com.wan37.logic.guild.service.donate.item;

import java.util.List;

/**
 * 请求公会捐献的背包物品封装接口
 *
 * @author linda
 */
public interface ReqGuildDonateItem {

    interface Factory {

        /**
         * 创建要捐献的背包物品接口实例
         *
         * @param args 要捐献的背包物品字符串参数
         * @return ReqGuildDonateItem
         */
        ReqGuildDonateItem create(String args);
    }

    /**
     * 返回要捐献的背包格子及相应数量
     *
     * @return List<GuildDonateItem>
     */
    List<GuildDonateItem> getDonateItems();
}
