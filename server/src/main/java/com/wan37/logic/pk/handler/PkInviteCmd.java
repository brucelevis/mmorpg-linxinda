package com.wan37.logic.pk.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.pk.service.PkInviteExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 决斗邀请
 */
@Service
class PkInviteCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private PkInviteExec pkInviteExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        if (Objects.equals(player.getUid(), uid)) {
            player.syncClient("不能决斗自己");
            return;
        }

        if (!playerGlobalManager.isOnline(uid)) {
            player.syncClient("对方没在线，不可决斗");
            return;
        }

        Player target = playerGlobalManager.getPlayerByUid(uid);
        pkInviteExec.exec(player, target);
    }
}
