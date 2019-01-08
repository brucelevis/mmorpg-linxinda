package com.wan37.logic.guild.entity;

import com.wan37.logic.guild.GuildPositionEnum;
import com.wan37.logic.guild.database.GuildMemberDb;

/**
 * 公会成员接口实例
 *
 * @author linda
 */
public interface GuildMember {

    interface Factory {

        /**
         * 创建公会成员接口实例
         *
         * @param guildMemberDb 公会成员数据库实例
         * @return GuildMember
         */
        GuildMember create(GuildMemberDb guildMemberDb);
    }

    /**
     * 返回公会成员数据库实例
     *
     * @return GuildMemberDb
     */
    GuildMemberDb getGuildMemberDb();

    /**
     * 玩家uid
     *
     * @return Long
     */
    Long getPlayerUid();

    /**
     * 返回公会职位id
     *
     * @return Integer
     * @see GuildPositionEnum#getId()
     */
    Integer getPosition();

    /**
     * 设置职位
     *
     * @param position 公会职位id
     */
    void setPosition(Integer position);

    /**
     * 成员名字
     *
     * @return String
     */
    String getName();
}
