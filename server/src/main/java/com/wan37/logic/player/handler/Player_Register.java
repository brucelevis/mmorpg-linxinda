package com.wan37.logic.player.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.service.register.PRegisterPlayer;
import com.wan37.logic.player.service.register.PlayerRegisterExec;
import com.wan37.server.GeneralReqMsg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创角
 */
@Service
public class Player_Register implements GeneralHandler {

    private static final Logger LOG = Logger.getLogger(Player_Register.class);

    @Autowired
    private PlayerRegisterExec playerRegisterExec;

    @Autowired
    private PRegisterPlayer.Factory factory;

    @Override
    public void handle(GeneralReqMsg msg) {
        LOG.info("player_Register");

        PRegisterPlayer player = factory.create(msg);
        playerRegisterExec.exec(player);
    }
}
