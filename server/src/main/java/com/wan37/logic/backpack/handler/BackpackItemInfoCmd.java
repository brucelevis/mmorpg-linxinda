package com.wan37.logic.backpack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.backpack.service.item.BackpackItemInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 背包某一物品详细信息
 */
@Service
class BackpackItemInfoCmd implements GeneralHandler {

    @Autowired
    private BackpackItemInfoExec backpackItemInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long uid = msg.getParamAsLong(1);
        backpackItemInfoExec.exec(msg.getPlayer(), uid);
    }
}
