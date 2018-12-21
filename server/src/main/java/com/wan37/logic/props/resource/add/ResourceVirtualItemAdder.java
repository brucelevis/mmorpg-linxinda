package com.wan37.logic.props.resource.add;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.VirtualItemAddEvent;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.database.CurrencyItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import com.wan37.logic.props.resource.ResourceElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceVirtualItemAdder {

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public boolean add(ResourceElement element, Player player) {
        Integer cfgId = element.getCfgId();
        VirtualItemCfg cfg = virtualItemCfgLoader.load(cfgId).orElse(null);
        if (cfg == null) {
            return false;
        }

        long amount = element.getAmount();
        CurrencyDb currencyDb = player.getPlayerDb().getCurrencyDb();
        if (!canAdd(cfg, amount, currencyDb)) {
            player.syncClient(String.format("%s已达上限", cfg.getName()));
            return false;
        }

        CurrencyItemDb itemDb = currencyDb.getItemMap().get(cfgId);
        if (itemDb == null) {
            itemDb = new CurrencyItemDb();
            itemDb.setId(cfgId);
            itemDb.setAmount(0);
            currencyDb.getItemMap().put(cfgId, itemDb);
        }

        itemDb.setAmount(itemDb.getAmount() + amount);
        currencyDb.getIds().add(cfgId);

        // 抛出加钱事件
        genernalEventListenersManager.fireEvent(new VirtualItemAddEvent(element.getCfgId(), element.getAmount(), player));

        return true;
    }

    private boolean canAdd(VirtualItemCfg cfg, long amount, CurrencyDb currencyDb) {
        Integer cfgId = cfg.getId();
        CurrencyItemDb itemDb = currencyDb.getItemMap().get(cfgId);

        long currentAmount = itemDb != null ? itemDb.getAmount() : 0;
        return currentAmount + amount <= cfg.getMaxOverlay();
    }
}
