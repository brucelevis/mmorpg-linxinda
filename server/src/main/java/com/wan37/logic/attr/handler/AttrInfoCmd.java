package com.wan37.logic.attr.handler;

import com.wan37.handler.GeneralHandler;
import com.wan37.handler.GeneralReqMsg;
import com.wan37.logic.attr.service.AttrInfoExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人物属性面板信息
 */
@Service
class AttrInfoCmd implements GeneralHandler {

    @Autowired
    private AttrInfoExec attrInfoExec;

    @Override
    public void handle(GeneralReqMsg msg) {
        attrInfoExec.exec(msg.getPlayer());
    }
}
