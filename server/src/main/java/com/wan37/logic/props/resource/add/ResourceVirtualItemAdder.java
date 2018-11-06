package com.wan37.logic.props.resource.add;

import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.database.CurrencyItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import com.wan37.logic.props.resource.ResourceElement;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceVirtualItemAdder {

    private static final Logger LOG = Logger.getLogger(ResourceVirtualItemAdder.class);

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    public void add(ResourceElement element, Player player) {
        Integer cfgId = element.getCfgId();
        VirtualItemCfg cfg = virtualItemCfgLoader.load(cfgId).orElse(null);
        if (cfg == null) {
            return;
        }

        long amount = element.getAmount();
        CurrencyDb currencyDb = player.getPlayerDb().getCurrencyDb();
        if (!canAdd(cfg, amount, currencyDb)) {
            LOG.info("虚拟物品已满");
            return;
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
    }

    private boolean canAdd(VirtualItemCfg cfg, long amount, CurrencyDb currencyDb) {
        Integer cfgId = cfg.getId();
        CurrencyItemDb itemDb = currencyDb.getItemMap().get(cfgId);

        long currentAmount = itemDb != null ? itemDb.getAmount() : 0;
        return currentAmount + amount <= cfg.getMaxOverlay();
    }
}
