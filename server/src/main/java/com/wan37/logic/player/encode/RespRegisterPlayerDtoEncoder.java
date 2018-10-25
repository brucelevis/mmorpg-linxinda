package com.wan37.logic.player.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.resp.RespRegisterPlayerDto;
import org.springframework.stereotype.Service;

@Service
public class RespRegisterPlayerDtoEncoder {

    public GeneralResponseDto encode(ResultCode resultCode, Long playerUid) {
        GeneralResponseDto dto = new GeneralResponseDto();
        dto.setResultCode(resultCode.getCode());
        dto.setDesc(resultCode.getDesc());
        dto.setContent(encodeContent(playerUid));
        return dto;
    }

    private RespRegisterPlayerDto encodeContent(Long playerUid) {
        RespRegisterPlayerDto dto = new RespRegisterPlayerDto();
        dto.setPlayerUid(playerUid);
        return dto;
    }
}
