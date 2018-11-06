package com.wan37.common.notify;

import com.wan37.common.resp.RespVirtualItemDto;

import java.util.List;

public class CurrencyUpdateNotify {

    private List<RespVirtualItemDto> items;

    public List<RespVirtualItemDto> getItems() {
        return items;
    }

    public void setItems(List<RespVirtualItemDto> items) {
        this.items = items;
    }
}
