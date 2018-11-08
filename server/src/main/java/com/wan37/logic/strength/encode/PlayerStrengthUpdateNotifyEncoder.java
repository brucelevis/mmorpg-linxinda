package com.wan37.logic.strength.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.notify.PlayerStrengthUpdateNotify;
import com.wan37.common.resp.RespPlayerStrengthAttrDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.attr.config.AttrCfg;
import com.wan37.logic.attr.config.AttrCfgLoader;
import com.wan37.logic.strength.database.PlayerStrengthDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayerStrengthUpdateNotifyEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    @Autowired
    private AttrCfgLoader attrCfgLoader;

    public GeneralResponseDto encode(ResultCode resultCode, PlayerStrengthDb playerStrengthDb) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(playerStrengthDb));
        return dto;
    }

    private PlayerStrengthUpdateNotify encodeContent(PlayerStrengthDb playerStrengthDb) {
        PlayerStrengthUpdateNotify dto = new PlayerStrengthUpdateNotify();
        dto.setBaseValue(playerStrengthDb.getBaseVal());

        dto.setAttrs(playerStrengthDb.getAttrs().entrySet().stream()
                .map(this::encodeAttr)
                .collect(Collectors.toList()));

        return dto;
    }

    private RespPlayerStrengthAttrDto encodeAttr(Map.Entry<Integer, Double> entry) {
        RespPlayerStrengthAttrDto dto = new RespPlayerStrengthAttrDto();
        dto.setAttrCfgId(entry.getKey());
        dto.setValue(entry.getValue());

        dto.setName(attrCfgLoader.load(entry.getKey())
                .map(AttrCfg::getName)
                .orElse(null));

        return dto;
    }
}
