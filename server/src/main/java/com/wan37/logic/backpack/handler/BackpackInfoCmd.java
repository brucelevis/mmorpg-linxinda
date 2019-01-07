package com.wan37.logic.backpack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.backpack.service.BackpackInfoExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 背包信息
 */
@Service
class BackpackInfoCmd implements GeneralHandler {

    @Autowired
    private BackpackInfoExec backpackInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        backpackInfoExec.exec(msg.getChannel());
    }
}
