package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.trade.service.TradeRejectExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 拒绝交易
 */
@Service
class TradeRejectCmd implements GeneralHandler {

    @Autowired
    private TradeRejectExec tradeRejectExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Long tradeUid = msg.getParamAsLong(1);
        tradeRejectExec.exec(msg.getPlayer(), tradeUid);
    }
}
