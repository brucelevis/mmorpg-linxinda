package com.wan37.logic.trade.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.Trade;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.entity.TradePlayer;
import com.wan37.logic.trade.init.TradePlayerCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class TradeAcceptExec {

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private TradePlayerCreator tradePlayerCreator;

    public void exec(Player to, Long tradeUid) {
        Trade trade = tradeGlobalManager.getTrade(tradeUid);
        if (trade == null) {
            throw new GeneralErrorException("交易已关闭");
        }

        if (!Objects.equals(trade.getToUid(), to.getUid())) {
            throw new GeneralErrorException("错误的交易uid");
        }

        // 接受交易
        ITrade toTrade = to.getTrade();
        try {
            toTrade.lock();

            if (toTrade.getUid() != null) {
                to.syncClient("你正在交易中，不能接受交易请求");
                return;
            }

            toTrade.setUid(trade.getUid());
            trade.getTradePlayerMap().put(to.getUid(), tradePlayerCreator.create(to));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toTrade.unLock();
        }

        // 接受推送
        try {
            //  交易锁
            trade.getLock().lock();

            Long fromUid = trade.getFromUid();
            TradePlayer fromTradePlayer = trade.getTradePlayerMap().get(fromUid);
            if (fromTradePlayer == null) {
                // 请求者取消交易
                to.syncClient("对方关闭了交易");
                return;
            }

            if (!playerGlobalManager.isOnline(fromUid)) {
                // 没在线
                to.syncClient("对方离线");
                return;
            }

            Player from = fromTradePlayer.getPlayer();
            from.syncClient(String.format("[%s]接受了你的交易请求", to.getName()));
            to.syncClient(String.format("你接受了[%s]的交易请求", from.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            trade.getLock().unlock();
        }
    }
}