package com.wan37.logic.friend.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.friend.service.FriendAcceptExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class FriendAcceptCmd implements GeneralHandler {

    @Autowired
    private FriendAcceptExec friendAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long id = msg.getParamAsLong(1);
        friendAcceptExec.exec(msg.getPlayer(), id);
    }
}
