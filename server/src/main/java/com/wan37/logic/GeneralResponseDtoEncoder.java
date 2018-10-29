package com.wan37.logic;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import org.springframework.stereotype.Service;

@Service
public class GeneralResponseDtoEncoder {

    public GeneralResponseDto encode(ResultCode resultCode) {
        GeneralResponseDto dto = new GeneralResponseDto();
        dto.setResultCode(resultCode.getCode());
        dto.setDesc(resultCode.getDesc());
        return dto;
    }
}
