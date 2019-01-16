package com.wan37.logic.trade;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易实体
 *
 * @author linda
 */
public class Trade {

    /**
     * 交易唯一id
     */
    private Long uid;

    /**
     * 发起者玩家uid
     */
    private Long inviterUid;

    /**
     * 目标玩家uid
     */
    private Long targetUid;

    /**
     * 交易中玩家
     */
    private Map<Long, TradePlayer> tradePlayerMap = new HashMap<>(2);

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Map<Long, TradePlayer> getTradePlayerMap() {
        return tradePlayerMap;
    }

    public void setTradePlayerMap(Map<Long, TradePlayer> tradePlayerMap) {
        this.tradePlayerMap = tradePlayerMap;
    }

    public Long getInviterUid() {
        return inviterUid;
    }

    public void setInviterUid(Long inviterUid) {
        this.inviterUid = inviterUid;
    }

    public Long getTargetUid() {
        return targetUid;
    }

    public void setTargetUid(Long targetUid) {
        this.targetUid = targetUid;
    }
}
