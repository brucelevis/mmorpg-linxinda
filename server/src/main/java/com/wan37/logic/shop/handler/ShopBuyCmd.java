package com.wan37.logic.shop.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.shop.service.ShopBuyExec;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商店购买
 */
@Service
class ShopBuyCmd implements GeneralHandler {

    @Autowired
    private ShopBuyExec shopBuyExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        String[] params = msg.getParams();

        Integer id = Integer.parseInt(params[1]);
        int amount = Integer.parseInt(params[2]);

        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        shopBuyExec.exec(player, id, amount);
    }
}
