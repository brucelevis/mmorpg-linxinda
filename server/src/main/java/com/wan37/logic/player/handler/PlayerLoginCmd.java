package com.wan37.logic.player.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.service.PlayerLoginExec;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录
 */
@Service
class PlayerLoginCmd implements GeneralHandler {

    @Autowired
    private PlayerLoginExec playerLoginExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long playerUid = msg.getParamAsLong(1);
        playerLoginExec.exec(playerUid, msg.getChannel());
    }
}
