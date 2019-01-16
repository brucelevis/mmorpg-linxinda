package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.trade.service.close.TradeCloseExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易关闭
 */
@Service
class TradeCloseCmd implements GeneralHandler {

    @Autowired
    private TradeCloseExec tradeCloseExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        tradeCloseExec.exec(msg.getPlayer());
    }
}
