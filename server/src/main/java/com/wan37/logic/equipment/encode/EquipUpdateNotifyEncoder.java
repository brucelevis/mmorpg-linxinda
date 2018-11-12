package com.wan37.logic.equipment.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.notify.EquipUpdateNotify;
import com.wan37.common.resp.RespEquipItemDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.database.EquipDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EquipUpdateNotifyEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, EquipDb equipDb) {
        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(encodeContent(equipDb));
        return dto;
    }

    private EquipUpdateNotify encodeContent(EquipDb equipDb) {
        EquipUpdateNotify dto = new EquipUpdateNotify();

        dto.setItems(equipDb.getParts().stream()
                .map(p -> encodeEquip(p, equipDb.getItems().get(p)))
                .collect(Collectors.toList()));

        return dto;
    }

    private RespEquipItemDto encodeEquip(Integer part, ItemDb itemDb) {
        RespEquipItemDto dto = new RespEquipItemDto();
        dto.setPart(part);

        if (itemDb == null) {
            return dto;
        }

        dto.setUid(itemDb.getUid());
        dto.setCfgId(itemDb.getCfgId());
        dto.setExtra(itemDb.getExtraDb());

        return dto;
    }
}
