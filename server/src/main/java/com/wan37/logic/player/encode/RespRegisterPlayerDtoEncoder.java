package com.wan37.logic.player.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.resp.RespRegisterPlayerDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespRegisterPlayerDtoEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, Long playerUid) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(playerUid));
        return dto;
    }

    private RespRegisterPlayerDto encodeContent(Long playerUid) {
        RespRegisterPlayerDto dto = new RespRegisterPlayerDto();
        dto.setPlayerUid(playerUid);
        return dto;
    }
}
