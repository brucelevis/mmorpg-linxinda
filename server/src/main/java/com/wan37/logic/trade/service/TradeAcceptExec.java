package com.wan37.logic.trade.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.ITrade;
import com.wan37.logic.trade.Trade;
import com.wan37.logic.trade.TradePlayer;
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

    public void exec(Player player, Long tradeUid) {
        Trade trade = tradeGlobalManager.getTrade(tradeUid);
        if (trade == null) {
            player.syncClient("交易已关闭");
            return;
        }

        if (!Objects.equals(trade.getToUid(), player.getUid())) {
            player.syncClient("错误的交易uid");
            return;
        }

        // 接受交易
        ITrade toTrade = player.getTrade();
        try {
            toTrade.lock();

            if (toTrade.getUid() != null) {
                player.syncClient("你正在交易中，不能接受交易请求");
                return;
            }

            toTrade.setUid(trade.getUid());
            trade.getTradePlayerMap().put(player.getUid(), tradePlayerCreator.create(player));
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
                player.syncClient("对方关闭了交易");
                return;
            }

            if (!playerGlobalManager.isOnline(fromUid)) {
                // 没在线
                player.syncClient("对方离线");
                return;
            }

            Player from = fromTradePlayer.getPlayer();
            from.syncClient(String.format("[%s]接受了你的交易请求", player.getName()));
            player.syncClient(String.format("你接受了[%s]的交易请求", from.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            trade.getLock().unlock();
        }
    }
}