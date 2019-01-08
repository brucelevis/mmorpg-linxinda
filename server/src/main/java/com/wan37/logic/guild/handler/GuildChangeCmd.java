package com.wan37.logic.guild.handler;

import com.wan37.exception.GeneralErrorException;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.guild.service.GuildChangeExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会修改权限
 */
@Service
class GuildChangeCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GuildChangeExec guildChangeExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        Player target = playerGlobalManager.getPlayerByUid(uid);
        if (target == null) {
            throw new GeneralErrorException("目标不存在");
        }

        guildChangeExec.exec(player, target);
    }
}
