package com.wan37.logic.team;

import java.util.List;

/**
 * 组队
 *
 * @author linda
 */
public interface Team {

    interface Factory {

        /**
         * 创建组队接口
         *
         * @param leaderUid 玩家队长uid
         * @return Team
         */
        Team create(Long leaderUid);
    }

    /**
     * 组队锁
     */
    void lock();

    /**
     * 组队解锁
     */
    void unLock();

    /**
     * 组队唯一id
     *
     * @return Long
     */
    Long getUid();

    /**
     * 玩家队长id
     *
     * @return Long
     */
    Long getLeaderUid();

    /**
     * 添加队员
     *
     * @param member 队员
     */
    void addMember(TeamMember member);

    /**
     * 移除队员
     *
     * @param playerUid 玩家uid
     */
    void rmMember(Long playerUid);

    /**
     * 获取所有队员
     *
     * @return List<TeamMember>
     */
    List<TeamMember> getMembers();

    /**
     * 返回指定成员
     *
     * @param playerUid 玩家uid
     * @return TeamMember
     */
    TeamMember getMember(Long playerUid);

    /**
     * 组队广播
     *
     * @param msg 广播信息
     */
    void broadcast(String msg);
}
