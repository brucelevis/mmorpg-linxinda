package com.wan37.logic.backpack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.backpack.service.BackpackCleanExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 背包整理
 */
@Service
class BackpackCleanCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private BackpackCleanExec backpackCleanExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        backpackCleanExec.exec(player);
    }
}
