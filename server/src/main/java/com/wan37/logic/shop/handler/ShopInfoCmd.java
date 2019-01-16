package com.wan37.logic.shop.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.shop.service.ShopInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商店信息
 */
@Service
class ShopInfoCmd implements GeneralHandler {

    @Autowired
    private ShopInfoExec shopInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        shopInfoExec.exec(msg.getPlayer());
    }
}
