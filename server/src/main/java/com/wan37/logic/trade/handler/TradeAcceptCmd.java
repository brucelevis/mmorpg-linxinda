package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.trade.service.TradeAcceptExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 同意交易申请
 */
@Service
class TradeAcceptCmd implements GeneralHandler {

    @Autowired
    private TradeAcceptExec tradeAcceptExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long tradeUid = msg.getParamAsLong(1);
        tradeAcceptExec.exec(msg.getPlayer(), tradeUid);
    }
}
