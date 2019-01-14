package com.wan37.logic.trade.service.add.money;

import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.encode.TradeEncoder;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.entity.Trade;
import com.wan37.logic.trade.entity.TradePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class TradeAddMoneyExec {

    @Autowired
    private ResourceFacade resourceFacade;

    @Autowired
    private TradeGlobalManager tradeGlobalManager;

    @Autowired
    private TradeEncoder tradeEncoder;

    @Autowired
    private CurrencyUpdateNotifier currencyUpdateNotifier;

    public void exec(Player player, Integer cfgId, long amount) {
        if (amount <= 0) {
            player.syncClient("要交易的钱必须为正数");
            return;
        }

        if (resourceFacade.queryCurrency(cfgId, player) < amount) {
            player.syncClient("要交易的钱不足");
            return;
        }

        ITrade iTrade = player.getTrade();
        if (iTrade.getUid() == null) {
            player.syncClient("未在交易");
            return;
        }

        Trade trade = tradeGlobalManager.getTrade(iTrade.getUid());
        if (trade == null) {
            player.syncClient("交易不存在");
            return;
        }

        try {
            trade.getLock().lock();

            TradePlayer me = trade.getTradePlayerMap().get(player.getUid());
            long curAmount = me.getCurrency().getOrDefault(cfgId, 0L);

            if (curAmount > amount) {
                // 之前钱比调整的还多
                ResourceElement e = new ResourceElementImpl(cfgId, curAmount - amount);
                resourceFacade.giveResource(e, player);
            } else if (curAmount < amount) {
                ResourceElement e = new ResourceElementImpl(cfgId, amount - curAmount);
                resourceFacade.consumeResource(e, player);
            } else {
                // 没变化
                return;
            }

            currencyUpdateNotifier.notify(player);
            me.getCurrency().put(cfgId, amount);

            String notify = tradeEncoder.encode(trade);
            trade.getTradePlayerMap().values().forEach(p -> p.getPlayer().syncClient(notify));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            trade.getLock().unlock();
        }
    }
}
