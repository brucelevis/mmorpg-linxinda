package com.wan37.logic.player.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.resp.RespLoginPlayerDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespLoginPlayerDtoEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, Player player) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(player));
        return dto;
    }

    private RespLoginPlayerDto encodeContent(Player player) {
        if (player == null) {
            return null;
        }

        RespLoginPlayerDto dto = new RespLoginPlayerDto();
        dto.setPlayerUid(player.getUid());
        dto.setName(player.getName());
        dto.setFactionId(player.getFactionId());
        dto.setLevel(player.getLevel());
        dto.setSceneId(player.getSceneId());
        return dto;
    }
}
