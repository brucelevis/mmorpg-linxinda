package com.wan37.common.notify;

import com.wan37.common.resp.RespBackpackItemDto;

import java.util.List;

public class BackpackUpdateNotify {

    /**
     * 变化的背包格子
     */
    private List<RespBackpackItemDto> items;

    public List<RespBackpackItemDto> getItems() {
        return items;
    }

    public void setItems(List<RespBackpackItemDto> items) {
        this.items = items;
    }
}
