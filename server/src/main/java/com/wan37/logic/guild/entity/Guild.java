package com.wan37.logic.guild.entity;

import com.wan37.logic.guild.database.GuildGlobalDb;

import java.util.List;

/**
 * 公会接口实例
 *
 * @author linda
 */
public interface Guild {

    interface Factory {

        /**
         * 创建公会接口实例，隔开公会数据库实例
         *
         * @param guildGlobalDb 公会数据库实例
         * @return Guild
         */
        Guild create(GuildGlobalDb guildGlobalDb);
    }

    /**
     * 公会唯一id
     *
     * @return Long
     */
    Long getUid();

    /**
     * 公会名
     *
     * @return String
     */
    String getName();

    /**
     * 最大人数上限
     *
     * @return int
     */
    int getMaxNum();

    /**
     * 当前人数
     *
     * @return int
     */
    int getCurNum();

    /**
     * 返回公会成员
     *
     * @return List<GuildMember>
     */
    List<GuildMember> getMembers();

    /**
     * 返回指定成员
     *
     * @param playerUid 玩家唯一id
     * @return GuildMember
     */
    GuildMember getMember(Long playerUid);

    /**
     * 添加成员
     *
     * @param member 公会成员
     */
    void addMember(GuildMember member);

    /**
     * 移除公会成员
     *
     * @param playerUid 玩家唯一id
     */
    void rmMember(Long playerUid);

    /**
     * 公会广播
     *
     * @param msg 消息
     */
    void notifyAll(String msg);

    /**
     * 公会数据持久化
     */
    void save();

    /**
     * 返回会长玩家uid
     *
     * @return Long
     */
    Long getLeaderUid();

    /**
     * 公会仓库
     *
     * @return GuildWarehouse
     */
    GuildWarehouse getWarehouse();

    /**
     * 锁
     */
    void lock();

    /**
     * 解锁
     */
    void unlock();
}
