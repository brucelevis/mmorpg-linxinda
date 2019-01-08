package com.wan37.logic.guild.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.guild.service.GuildCreateExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建公会
 */
@Service
class GuildCreateCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GuildCreateExec guildCreateExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        String name = msg.getParamAsString(1);
        guildCreateExec.exec(player, name);
    }
}
