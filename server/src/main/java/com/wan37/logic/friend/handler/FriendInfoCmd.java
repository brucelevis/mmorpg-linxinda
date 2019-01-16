package com.wan37.logic.friend.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.friend.service.FriendInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class FriendInfoCmd implements GeneralHandler {

    @Autowired
    private FriendInfoExec friendInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        friendInfoExec.exec(msg.getPlayer());
    }
}
