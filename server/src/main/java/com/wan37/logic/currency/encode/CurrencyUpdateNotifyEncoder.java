package com.wan37.logic.currency.encode;

import com.wan37.common.GeneralResponseDto;
import com.wan37.common.ResultCode;
import com.wan37.common.notify.CurrencyUpdateNotify;
import com.wan37.common.resp.RespVirtualItemDto;
import com.wan37.logic.GeneralResponseDtoEncoder;
import com.wan37.logic.currency.database.CurrencyDb;
import com.wan37.logic.currency.database.CurrencyItemDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrencyUpdateNotifyEncoder {

    @Autowired
    private GeneralResponseDtoEncoder generalResponseDtoEncoder;

    public GeneralResponseDto encode(ResultCode resultCode, CurrencyDb currencyDb) {
        CurrencyUpdateNotify content = encodeContent(currencyDb);
        if (content == null) {
            return null;
        }

        GeneralResponseDto dto = generalResponseDtoEncoder.encode(resultCode);
        dto.setContent(content);
        return dto;
    }

    private CurrencyUpdateNotify encodeContent(CurrencyDb currencyDb) {
        Set<Integer> ids = currencyDb.getIds();
        if (ids.isEmpty()) {
            return null;
        }

        CurrencyUpdateNotify dto = new CurrencyUpdateNotify();

        dto.setItems(currencyDb.getItemMap().values().stream()
                .filter(i -> ids.contains(i.getId()))
                .map(this::encodeItem)
                .collect(Collectors.toList()));

        return dto;
    }

    private RespVirtualItemDto encodeItem(CurrencyItemDb itemDb) {
        RespVirtualItemDto dto = new RespVirtualItemDto();

        dto.setCfgId(itemDb.getId());
        dto.setAmount(itemDb.getAmount());

        return dto;
    }
}
