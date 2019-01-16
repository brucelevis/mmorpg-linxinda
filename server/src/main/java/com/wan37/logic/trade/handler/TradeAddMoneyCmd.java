package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.trade.service.add.money.TradeAddMoneyExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易添加虚物（钱）
 */
@Service
class TradeAddMoneyCmd implements GeneralHandler {

    @Autowired
    private TradeAddMoneyExec tradeAddMoneyExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer cfgId = msg.getParamAsInt(1);
        long amount = msg.getParamAsLong(2);

        tradeAddMoneyExec.exec(msg.getPlayer(), cfgId, amount);
    }
}
