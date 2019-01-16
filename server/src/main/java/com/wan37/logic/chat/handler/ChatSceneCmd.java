package com.wan37.logic.chat.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.chat.service.ChatSceneExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 当前场景聊天
 */
@Service
class ChatSceneCmd implements GeneralHandler {

    @Autowired
    private ChatSceneExec chatSceneExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String content = msg.getParamAsString(1);
        chatSceneExec.exec(msg.getPlayer(), content);
    }
}
