package com.wan37.logic.trade.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.player.Player;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.GTrade;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.init.TradePlayerCreator;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TradeRequestExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private TradePlayerCreator tradePlayerCreator;

    public void exec(Player from, Player to) {
        if (to.getTrade().getUid() != null) {
            throw new GeneralErrorException("对方正在交易中，不能发起交易请求");
        }

        ITrade fromTrade = from.getTrade();
        try {
            fromTrade.lock();

            if (fromTrade.getUid() != null) {
                from.syncClient("你正在交易中，不能发起交易请求");
                return;
            }

            // 创建交易
            GTrade trade = createTrade(from, to);
            tradeGlobalManager.addTrade(trade);

            fromTrade.setUid(trade.getUid());

            from.syncClient(String.format("你向[%s]发起了交易请求", to.getName()));
            to.syncClient(String.format("[%s]向你发起了交易请求（tradeUid：%s）", from.getName(), trade.getUid()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fromTrade.unLock();
        }
    }

    private GTrade createTrade(Player from, Player to) {
        GTrade trade = new GTrade();
        trade.setUid(IdUtil.generate());
        trade.setLock(new ReentrantLock());
        trade.setTradePlayerMap(new HashMap<>());

        trade.setFromUid(from.getUid());
        trade.getTradePlayerMap().put(from.getUid(), tradePlayerCreator.create(from));

        trade.setToUid(to.getUid());
        return trade;
    }
}
