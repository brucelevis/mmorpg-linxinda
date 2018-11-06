package com.wan37.logic.props.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.notify.BackpackUpdateNotify;
import com.wan37.common.resp.RespBackpackItemDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BackpackUpdateNotifyEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, BackpackDb backpackDb) {
        BackpackUpdateNotify content = encodeContent(backpackDb);
        if (content == null) {
            return null;
        }

        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(content);
        return dto;
    }

    private BackpackUpdateNotify encodeContent(BackpackDb backpackDb) {
        Set<Integer> indexs = backpackDb.getIndexs();
        if (indexs.isEmpty()) {
            return null;
        }

        BackpackUpdateNotify dto = new BackpackUpdateNotify();

        dto.setItems(backpackDb.getItemMap().values().stream()
                .filter(i -> indexs.contains(i.getIndex()))
                .map(this::encodeItem)
                .collect(Collectors.toList()));

        return dto;
    }

    private RespBackpackItemDto encodeItem(ItemDb itemDb) {
        RespBackpackItemDto dto = new RespBackpackItemDto();

        dto.setUid(itemDb.getUid());
        dto.setCfgId(itemDb.getCfgId());
        dto.setIndex(itemDb.getIndex());
        dto.setName(itemDb.getName());
        dto.setAmount(itemDb.getAmount());

        return dto;
    }

}
