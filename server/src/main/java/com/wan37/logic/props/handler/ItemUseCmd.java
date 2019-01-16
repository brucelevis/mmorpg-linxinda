package com.wan37.logic.props.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.props.service.ItemUseExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 使用背包物品
 */
@Service
class ItemUseCmd implements GeneralHandler {

    @Autowired
    private ItemUseExec itemUseExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer index = msg.getParamAsInt(1);
        itemUseExec.exec(msg.getPlayer(), index);
    }
}
