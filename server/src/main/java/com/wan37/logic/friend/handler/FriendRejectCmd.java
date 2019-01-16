package com.wan37.logic.friend.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.friend.service.FriendRejectExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class FriendRejectCmd implements GeneralHandler {

    @Autowired
    private FriendRejectExec friendRejectExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long id = msg.getParamAsLong(1);
        friendRejectExec.exec(msg.getPlayer(), id);
    }
}
