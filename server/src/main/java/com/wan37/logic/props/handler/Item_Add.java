package com.wan37.logic.props.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.props.service.add.ItemAddExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Item_Add implements GeneralHandler {

    @Autowired
    private ItemAddExec itemAddExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String[] params = msg.getParams();
        Integer cfgId = Integer.parseInt(params[1]);
        long amount = Long.parseLong(params[2]);
        String channelId = msg.getChannel().id().asLongText();

        itemAddExec.exec(cfgId, amount, channelId);
    }
}
