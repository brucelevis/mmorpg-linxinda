package com.wan37.logic.pk;

/**
 * 玩家决斗字段
 *
 * @author linda
 */
public interface Pk {

    /**
     * 是否锁住
     *
     * @return boolean
     */
    boolean isLock();

    /**
     * 尝试锁住
     *
     * @return boolean
     */
    boolean tryLock();

    /**
     * 解锁
     */
    void unLock();

    /**
     * 是否正在pk
     *
     * @return boolean
     */
    boolean isPking();

    /**
     * 设置pk字段
     *
     * @param isPking isPking
     */
    void setPking(boolean isPking);

    /**
     * 保存邀请者玩家uid
     *
     * @param playerUid 玩家唯一id
     */
    void addInviter(Long playerUid);

    /**
     * 是否已邀请过
     *
     * @param playerUid 玩家唯一id
     * @return boolean
     */
    boolean hadInvited(Long playerUid);

    /**
     * 移除邀请者
     *
     * @param playerUid 玩家唯一id
     */
    void rmInviter(Long playerUid);
}
