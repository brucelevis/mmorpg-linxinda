package com.wan37.logic.props.resource.sub;

import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.database.CurrencyItemDb;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.resource.ResourceElement;
import org.springframework.stereotype.Service;

/**
 * 消耗虚物
 *
 * @author linda
 */
@Service
public class ResourceVirtualItemSuber {

    public boolean sub(ResourceElement element, Player player) {
        Integer cfgId = element.getCfgId();
        long amount = element.getAmount();

        CurrencyDb currencyDb = player.getPlayerDb().getCurrencyDb();
        CurrencyItemDb itemDb = currencyDb.getItemMap().get(element.getCfgId());
        if (itemDb == null || itemDb.getAmount() < amount) {
            return false;
        }

        long cur = itemDb.getAmount();
        itemDb.setAmount(cur - amount);
        currencyDb.getIds().add(cfgId);

        if (cur == amount) {
            currencyDb.getItemMap().remove(cfgId);
        }

        return true;
    }
}
