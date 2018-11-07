package com.wan37.logic.player.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.notify.PlayerUpdateNotify;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerUpdateNotifyEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, PlayerDb playerDb) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(playerDb));
        return dto;
    }

    private PlayerUpdateNotify encodeContent(PlayerDb playerDb) {
        PlayerUpdateNotify dto = new PlayerUpdateNotify();

        dto.setHp(playerDb.getHp());
        dto.setMp(playerDb.getMp());
        dto.setLevel(playerDb.getLevel());
        dto.setExp(playerDb.getExp());

        return dto;
    }
}
