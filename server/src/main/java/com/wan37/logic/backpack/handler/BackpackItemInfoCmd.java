package com.wan37.logic.backpack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.backpack.service.item.BackpackItemInfoExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 背包某一物品详细信息
 */
@Service
class BackpackItemInfoCmd implements GeneralHandler {

    @Autowired
    private BackpackItemInfoExec backpackItemInfoExec;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        String[] params = msg.getParams();

        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        long uid = Long.parseLong(params[1]);
        backpackItemInfoExec.exec(player, uid);
    }
}
