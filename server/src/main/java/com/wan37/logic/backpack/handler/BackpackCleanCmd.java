package com.wan37.logic.backpack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.backpack.service.BackpackCleanExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 背包整理
 */
@Service
class BackpackCleanCmd implements GeneralHandler {

    @Autowired
    private BackpackCleanExec backpackCleanExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        backpackCleanExec.exec(msg.getPlayer());
    }
}
