package com.wan37.logic.dungeon.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.dungeon.service.DungeonLeaveExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 离开副本
 */
@Service
class DungeonLeaveCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private DungeonLeaveExec dungeonLeaveExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (channel == null) {
            return;
        }

        dungeonLeaveExec.exec(player);
    }
}
