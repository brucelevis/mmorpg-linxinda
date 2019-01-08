package com.wan37.logic.guild.service.get.item;

import java.util.List;

/**
 * 请求要取的公会物品封装的接口实例
 *
 * @author linda
 */
public interface ReqGuildGetItem {

    interface Factory {

        /**
         * 创建请求要取的公会物品封装的接口实例
         *
         * @param args 要取的物品字符串参数
         * @return ReqGuildGetItem
         */
        ReqGuildGetItem create(String args);
    }

    /**
     * 返回要取得公会物品及相应数量
     *
     * @return List<GuildGetItem>
     */
    List<GuildGetItem> getItems();
}
