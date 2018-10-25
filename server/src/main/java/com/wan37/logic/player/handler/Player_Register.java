package com.wan37.logic.player.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.server.GeneralReqMsg;
import org.springframework.stereotype.Service;

@Service
public class Player_Register implements GeneralHandler {

    @Override
    public void handle(GeneralReqMsg msg) {
        System.out.println("player_Register");
    }
}
