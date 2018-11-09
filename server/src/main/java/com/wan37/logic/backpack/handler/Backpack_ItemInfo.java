package com.wan37.logic.backpack.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.logic.backpack.service.item.BackpackItemInfoExec;
import com.wan37.server.GeneralReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Backpack_ItemInfo implements GeneralHandler {

    @Autowired
    private BackpackItemInfoExec backpackItemInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        String channelId = msg.getChannel().id().asLongText();
        String[] params = msg.getParams();

        long uid = Long.parseLong(params[1]);
        backpackItemInfoExec.exec(channelId, uid);
    }
}
