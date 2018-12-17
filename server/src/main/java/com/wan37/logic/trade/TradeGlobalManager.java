package com.wan37.logic.trade;

import com.wan37.logic.trade.entity.GTrade;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TradeGlobalManager {

    private static Map<Long, GTrade> tradeMap = new ConcurrentHashMap<>();

    public void addTrade(GTrade GTrade) {
        tradeMap.put(GTrade.getUid(), GTrade);
    }

    public GTrade getTrade(Long uid) {
        return tradeMap.get(uid);
    }

    public void rmTrade(Long uid) {
        tradeMap.remove(uid);
    }
}
