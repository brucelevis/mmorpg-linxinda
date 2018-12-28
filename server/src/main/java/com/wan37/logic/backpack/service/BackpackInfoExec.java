package com.wan37.logic.backpack.service;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.encode.BackpackInfoEncoder;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.encode.CurrencyInfoEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BackpackInfoExec {

    private static final Logger LOG = Logger.getLogger(BackpackInfoExec.class);

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private BackpackInfoEncoder backpackInfoEncoder;

    @Autowired
    private CurrencyInfoEncoder currencyInfoEncoder;

    public void exec(Channel channel) {
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            LOG.info("找不到玩家");
            return;
        }

        BackpackDb backpackDb = player.getPlayerDb().getBackpackDb();
        String props = backpackInfoEncoder.encode(backpackDb);
        player.syncClient(props);

        CurrencyDb currencyDb = player.getPlayerDb().getCurrencyDb();
        String currency = currencyInfoEncoder.encode(currencyDb);
        player.syncClient(currency);
    }
}
