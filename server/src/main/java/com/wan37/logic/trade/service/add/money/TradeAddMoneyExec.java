package com.wan37.logic.trade.service.add.money;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.currency.encode.CurrencyUpdateNotifier;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.ResourceFacade;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.logic.props.resource.impl.ResourceElementImpl;
import com.wan37.logic.trade.TradeGlobalManager;
import com.wan37.logic.trade.entity.GTrade;
import com.wan37.logic.trade.entity.ITrade;
import com.wan37.logic.trade.entity.TradePlayer;
import com.wan37.logic.trade.encode.TradeEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new GeneralErrorExecption("要交易的钱必须为正数");
        }

        if (resourceFacade.queryCurrency(cfgId, player) < amount) {
            throw new GeneralErrorExecption("要交易的钱不足");
        }

        ITrade iTrade = player.getTrade();
        if (iTrade.getUid() == null) {
            throw new GeneralErrorExecption("未在交易");
        }

        GTrade trade = tradeGlobalManager.getTrade(iTrade.getUid());
        if (trade == null) {
            throw new GeneralErrorExecption("交易不存在");
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
