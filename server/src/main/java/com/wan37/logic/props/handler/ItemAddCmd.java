package com.wan37.logic.props.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.props.service.ItemAddExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 添加物品（实物，虚物）
 */
@Service
class ItemAddCmd implements GeneralHandler {

    @Autowired
    private ItemAddExec itemAddExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer cfgId = msg.getParamAsInt(1);
        long amount = msg.getParamAsLong(2);

        itemAddExec.exec(msg.getPlayer(), cfgId, amount);
    }
}
