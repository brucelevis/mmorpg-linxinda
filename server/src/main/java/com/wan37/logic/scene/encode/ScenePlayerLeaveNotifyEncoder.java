package com.wan37.logic.scene.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.notify.ScenePlayerLeaveNotify;
import com.wan37.logic.GeneralResponseDtoEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScenePlayerLeaveNotifyEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, Long playerUid) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(playerUid));
        return dto;
    }

    private ScenePlayerLeaveNotify encodeContent(Long playerUid) {
        ScenePlayerLeaveNotify dto = new ScenePlayerLeaveNotify();
        dto.setPlayerUid(playerUid);
        return dto;
    }

}
