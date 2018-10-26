package com.wan37.logic.player.service.login.impl;

import com.wan37.logic.player.service.login.PLoginPlayer;
import com.wan37.server.GeneralReqMsg;
import org.springframework.stereotype.Service;

@Service
public class PLoginPlayerFactoryImpl implements PLoginPlayer.Factory {
    @Override
    public PLoginPlayer create(GeneralReqMsg msg) {
        return null;
    }
}
