package com.wan37.logic.player.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.service.login.PLoginPlayer;
import com.wan37.logic.player.service.login.PlayerLoginExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Player_Login implements GeneralHandler {

    @Autowired
    private PlayerLoginExec playerLoginExec;

    @Autowired
    private PLoginPlayer.Factory factory;

    @Override
    public void handle(GeneralReqMsg msg) {
        PLoginPlayer player = factory.create(msg);
        playerLoginExec.exec(player);
    }
}
