package com.wan37.logic.trade.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.trade.service.add.money.TradeAddMoneyExec;
import com.wan37.handler.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易添加虚物（钱）
 */
@Service
class TradeAddMoneyCmd implements GeneralHandler {

    @Autowired
    private PlayerGlobalManager playerGlsobalManager;

    @Autowired
    private TradeAddMoneyExec tradeAddMoneyExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        //FIXME: 重复代码
        Player player = playerGlsobalManager.getPlayerByChannel(msg.getChannel());
        if (player == null) {
            return;
        }

        Integer cfgId = msg.getParamAsInt(1);
        long amount = msg.getParamAsLong(2);
        tradeAddMoneyExec.exec(player, cfgId, amount);
    }
}
