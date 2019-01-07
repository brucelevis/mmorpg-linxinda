package com.wan37.logic.team.handler;

import com.wan37.exception.GeneralErrorException;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.service.TeamKickExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Team_Kick implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private TeamKickExec teamKickExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        //FIXME: 代码重复，需优化
        Player player = playerGlobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Long uid = msg.getParamAsLong(1);
        Player target = playerGlobalManager.getPlayerByUid(uid);
        if (target == null) {
            throw new GeneralErrorException("目标不存在");
        }

        teamKickExec.exec(player, target);
    }
}
