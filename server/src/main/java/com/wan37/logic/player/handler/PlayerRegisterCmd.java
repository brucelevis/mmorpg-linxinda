package com.wan37.logic.player.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.service.PlayerRegisterExec;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创角
 */
@Service
class PlayerRegisterCmd implements GeneralHandler {

    @Autowired
    private PlayerRegisterExec playerRegisterExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer factionId = msg.getParamAsInt(1);
        String name = msg.getParamAsString(2);

        playerRegisterExec.exec(factionId, name, msg.getChannel());
    }
}
