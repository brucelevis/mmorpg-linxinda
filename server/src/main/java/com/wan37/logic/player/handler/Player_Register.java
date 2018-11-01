package com.wan37.logic.player.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.service.register.PRegisterPlayer;
import com.wan37.logic.player.service.register.PlayerRegisterExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创角
 */
@Service
public class Player_Register implements GeneralHandler {

    @Autowired
    private PlayerRegisterExec playerRegisterExec;

    @Autowired
    private PRegisterPlayer.Factory factory;

    @Override
    public void handle(GeneralReqMsg msg) {
        PRegisterPlayer player = factory.create(msg);
        playerRegisterExec.exec(player);
    }
}
