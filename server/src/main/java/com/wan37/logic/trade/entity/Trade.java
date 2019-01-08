package com.wan37.logic.trade.entity;

import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * 交易实体
 *
 * @author linda
 */
public class Trade {

    private Long uid;

    private Long fromUid;

    private Long toUid;

    /**
     * key：playerUid
     */
    private Map<Long, TradePlayer> tradePlayerMap;

    private Lock lock;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getFromUid() {
        return fromUid;
    }

    public void setFromUid(Long fromUid) {
        this.fromUid = fromUid;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    public Map<Long, TradePlayer> getTradePlayerMap() {
        return tradePlayerMap;
    }

    public void setTradePlayerMap(Map<Long, TradePlayer> tradePlayerMap) {
        this.tradePlayerMap = tradePlayerMap;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
