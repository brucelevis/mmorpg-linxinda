package com.wan37.logic.backpack.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.resp.RespBackpackInfoDto;
import com.wan37.common.resp.RespBackpackItemDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RespBackpackInfoDtoEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, BackpackDb backpackDb) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(backpackDb));
        return dto;
    }

    private RespBackpackInfoDto encodeContent(BackpackDb backpackDb) {
        RespBackpackInfoDto dto = new RespBackpackInfoDto();
        dto.setCapacity(backpackDb.getCapacity());

        dto.setItems(backpackDb.getItemMap().values().stream()
                .map(this::encodeItem)
                .collect(Collectors.toList()));

        return dto;
    }

    private RespBackpackItemDto encodeItem(ItemDb itemDb) {
        RespBackpackItemDto dto = new RespBackpackItemDto();

        dto.setUid(itemDb.getUid());
        dto.setCfgId(itemDb.getCfgId());
        dto.setName(itemDb.getName());
        dto.setIndex(itemDb.getIndex());
        dto.setAmount(itemDb.getAmount());

        return dto;
    }
}
