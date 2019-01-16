package com.wan37.logic.friend.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.friend.service.FriendAddExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class FriendAddCmd implements GeneralHandler {

    @Autowired
    private FriendAddExec friendAddExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long uid = msg.getParamAsLong(1);
        friendAddExec.exec(msg.getPlayer(), uid);
    }
}
