package com.wan37.logic.scene.service.aoi;

import com.wan37.common.GeneralResponseDto;
import com.wan37.server.GeneralReqMsg;
import io.netty.channel.Channel;


public interface SAoiScene {

    interface Factory {

        SAoiScene create(GeneralReqMsg msg);
    }

    Channel getChannel();

    void response(GeneralResponseDto dto);
}
