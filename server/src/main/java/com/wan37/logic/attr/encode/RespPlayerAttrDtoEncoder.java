package com.wan37.logic.attr.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.resp.RespPAttrDto;
import com.wan37.common.resp.RespPlayerAttrDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.attr.database.PlayerAttrDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RespPlayerAttrDtoEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    public GeneralResponseDto encode(ResultCode resultCode, PlayerAttrDb attrDb) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(attrDb));
        return dto;
    }

    private RespPlayerAttrDto encodeContent(PlayerAttrDb attrDb) {
        RespPlayerAttrDto dto = new RespPlayerAttrDto();

        dto.setAttrs(attrDb.getAttrs().values().stream()
                .map(this::encodeAttr)
                .collect(Collectors.toList()));

        return dto;
    }

    private RespPAttrDto encodeAttr(PAttrDb attrDb) {
        RespPAttrDto dto = new RespPAttrDto();
        dto.setId(attrDb.getCfgId());
        dto.setValue(attrDb.getValue());

        dto.setName(attrCfgLoader.load(attrDb.getCfgId())
                .map(AttrCfg::getName)
                .orElse(null));

        return dto;
    }
}
