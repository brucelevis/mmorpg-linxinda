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

import java.util.Map;
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

        Map<Integer, ItemDb> items = backpackDb.getItemMap();
        dto.setItems(indexs.stream()
                .map(i -> encodeItem(i, items.get(i)))
                .collect(Collectors.toList()));

        return dto;
    }

    private RespBackpackItemDto encodeItem(Integer index, ItemDb itemDb) {
        RespBackpackItemDto dto = new RespBackpackItemDto();
        dto.setIndex(index);

        if (itemDb == null) {
            // 背包格子没物品
            dto.setAmount(0);
            return dto;
        }

        dto.setUid(itemDb.getUid());
        dto.setCfgId(itemDb.getCfgId());
        dto.setName(itemDb.getName());
        dto.setAmount(itemDb.getAmount());
        dto.setExtra(itemDb.getExtraDb());

        return dto;
    }

}
