package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.trade.service.TradeCommitExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 确认交易
 */
@Service
class TradeCommitCmd implements GeneralHandler {

    @Autowired
    private TradeCommitExec tradeCommitExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        tradeCommitExec.exec(msg.getPlayer());
    }
}
