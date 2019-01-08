package com.wan37.logic.trade;

import com.wan37.logic.trade.entity.Trade;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 交易全局管理器
 *
 * @author linda
 */
@Service
public class TradeGlobalManager {

    private static Map<Long, Trade> tradeMap = new ConcurrentHashMap<>();

    public void addTrade(Trade Trade) {
        tradeMap.put(Trade.getUid(), Trade);
    }

    public Trade getTrade(Long uid) {
        return tradeMap.get(uid);
    }

    public void rmTrade(Long uid) {
        tradeMap.remove(uid);
    }
}
