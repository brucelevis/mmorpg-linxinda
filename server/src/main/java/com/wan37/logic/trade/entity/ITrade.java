package com.wan37.logic.trade.entity;

/**
 * 玩家交易字段信息
 * <p>
 * FIXME: 代码简介之道：接口不起I开头，很low。
 *
 * @author linda
 */
public interface ITrade {

    /**
     * 玩家正在交易的uid，没有则返回Null
     *
     * @return Long
     */
    Long getUid();

    /**
     * 设置正在交易uid
     *
     * @param uid 交易uid
     */
    void setUid(Long uid);

    /**
     * 锁住交易字段
     */
    void lock();

    /**
     * 解锁交易字段
     */
    void unLock();
}
