package com.wan37.logic.props.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.props.service.use.ItemUseExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class Item_Use implements GeneralHandler {

    @Autowired
    private ItemUseExec itemUseExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        String[] params = msg.getParams();
        Integer index = Integer.parseInt(params[1]);

        itemUseExec.exec(channelId, index);
    }
}
