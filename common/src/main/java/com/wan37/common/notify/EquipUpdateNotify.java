package com.wan37.common.notify;

import com.wan37.common.resp.RespEquipItemDto;

import java.util.List;

public class EquipUpdateNotify {

    private List<RespEquipItemDto> items;

    public List<RespEquipItemDto> getItems() {
        return items;
    }

    public void setItems(List<RespEquipItemDto> items) {
        this.items = items;
    }
}
