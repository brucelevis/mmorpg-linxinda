package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.trade.service.add.item.TradeAddItemExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易添加实物
 */
@Service
class TradeAddItemCmd implements GeneralHandler {

    @Autowired
    private TradeAddItemExec tradeAddItemExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer index = msg.getParamAsInt(1);
        int amount = msg.getParamAsInt(2);

        tradeAddItemExec.exec(msg.getPlayer(), index, amount);
    }
}
