package com.wan37.logic.trade.entity;

public interface ITrade {

    Long getUid();

    void setUid(Long uid);

    void lock();

    void unLock();
}
