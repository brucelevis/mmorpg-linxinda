package com.wan37.logic.team;

/**
 * 队员接口
 *
 * @author linda
 */
public interface TeamMember {

    interface Factory {

        /**
         * 生成队员接口类
         *
         * @param playerUid 玩家uid
         * @return TeamMember
         */
        TeamMember create(Long playerUid);
    }

    /**
     * 玩家uid
     *
     * @return Long
     */
    Long getPlayerUid();

    /**
     * 是否在线
     *
     * @return boolean
     */
    boolean isOnline();
}
