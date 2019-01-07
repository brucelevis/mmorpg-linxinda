package com.wan37.logic.pk.handler;

import com.wan37.exception.GeneralErrorException;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.pk.service.PkInviteExec;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
class Pk_Invite implements GeneralHandler {

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
            throw new GeneralErrorException("不能决斗自己");
        }

        if (!playerGlobalManager.isOnline(uid)) {
            throw new GeneralErrorException("对方没在线，不可决斗");
        }

        Player target = playerGlobalManager.getPlayerByUid(uid);
        pkInviteExec.exec(player, target);
    }
}
