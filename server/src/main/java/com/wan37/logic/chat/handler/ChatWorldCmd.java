package com.wan37.logic.chat.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.chat.service.ChatWorldExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 世界聊天
 */
@Service
class ChatWorldCmd implements GeneralHandler {

    @Autowired
    private ChatWorldExec chatWorldExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String content = msg.getParamAsString(1);
        chatWorldExec.exec(msg.getPlayer(), content);
    }
}
