package com.wan37.logic.dungeon.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.dungeon.service.DungeonEnterExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 副本进入
 */
@Service
class Dungeon_Enter implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private DungeonEnterExec dungeonEnterExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Channel channel = msg.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        Integer dungeonId = msg.getParamAsInt(1);
        dungeonEnterExec.exec(player, dungeonId);
    }
}
