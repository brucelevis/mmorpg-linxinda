package com.wan37.logic.shop.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.shop.service.ShopBuyExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商店购买
 */
@Service
class ShopBuyCmd implements GeneralHandler {

    @Autowired
    private ShopBuyExec shopBuyExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        Integer id = msg.getParamAsInt(1);
        int amount = msg.getParamAsInt(2);

        shopBuyExec.exec(msg.getPlayer(), id, amount);
    }
}
