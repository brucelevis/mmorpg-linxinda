package com.wan37.logic.trade.entity;

import java.util.concurrent.locks.Lock;

/**
 * @author linda
 */
public class ITradeImpl implements ITrade {

    public ITradeImpl(Long tradeUid, Lock lock) {
        this.tradeUid = tradeUid;
        this.lock = lock;
    }

    @Override
    public Long getUid() {
        return tradeUid;
    }

    @Override
    public void setUid(Long uid) {
        tradeUid = uid;
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unLock() {
        lock.unlock();
    }

    private Long tradeUid;
    private final Lock lock;
}
