package com.wan37.logic.chat.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.chat.service.ChatPrivateExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 私聊
 */
@Service
class ChatPrivateCmd implements GeneralHandler {

    @Autowired
    private ChatPrivateExec chatPrivateExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long uid = msg.getParamAsLong(1);
        String content = msg.getParamAsString(2);

        chatPrivateExec.exec(msg.getPlayer(), uid, content);
    }
}
