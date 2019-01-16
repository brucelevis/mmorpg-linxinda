package com.wan37.logic.backpack.service;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.encode.BackpackInfoEncoder;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.encode.CurrencyInfoEncoder;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 背包信息打印
 *
 * @author linda
 */
@Service
public class BackpackInfoExec {

    @Autowired
    private BackpackInfoEncoder backpackInfoEncoder;

    @Autowired
    private CurrencyInfoEncoder currencyInfoEncoder;

    public void exec(Player player) {
        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        String props = backpackInfoEncoder.encode(backpackDb);
        player.syncClient(props);

        CurrencyDb currencyDb = player.getPlayerDb().getCurrencyDb();
        String currency = currencyInfoEncoder.encode(currencyDb);
        player.syncClient(currency);
    }
}
