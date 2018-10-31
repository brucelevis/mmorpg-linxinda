package com.wan37.logic.scene.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.notify.ScenePlayerEnterNotify;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.scene.player.ScenePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScenePlayerEnterNotifyEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, ScenePlayer player) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeNotify(player));

        return dto;
    }

    private ScenePlayerEnterNotify encodeNotify(ScenePlayer player) {
        ScenePlayerEnterNotify dto = new ScenePlayerEnterNotify();

        dto.setPlayerUid(player.getPlayerUid());
        dto.setName(player.getPlayerName());
        dto.setFactionId(player.getFactionId());
        dto.setLevel(player.getLevel());

        return dto;
    }
}
