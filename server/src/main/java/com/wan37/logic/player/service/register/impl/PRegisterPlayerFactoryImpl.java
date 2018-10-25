package com.wan37.logic.player.service.register.impl;

import com.wan37.logic.player.service.register.PRegisterPlayer;
import com.wan37.server.GeneralReqMsg;
import org.springframework.stereotype.Service;

@Service
public class PRegisterPlayerFactoryImpl implements PRegisterPlayer.Factory {

    @Override
    public PRegisterPlayer create(GeneralReqMsg msg) {
        return new PlayerImpl(msg.getParams(), msg.getChannel());
    }
}
