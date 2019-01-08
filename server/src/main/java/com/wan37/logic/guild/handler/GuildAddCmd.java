package com.wan37.logic.guild.handler;

import com.wan37.exception.GeneralErrorException;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.guild.service.GuildAddExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 公会加人
 */
@Service
class GuildAddCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GuildAddExec guildAddExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        if (!playerGlobalManager.isOnline(uid)) {
            throw new GeneralErrorException("对方不在线，公会邀请只能在线才能添加");
        }

        if (Objects.equals(player.getUid(), uid)) {
            throw new GeneralErrorException("不能加自己");
        }

        Player target = playerGlobalManager.getPlayerByUid(uid);
        guildAddExec.exec(player, target);
    }
}
