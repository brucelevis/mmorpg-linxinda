package com.wan37.common.resp;

import java.util.List;

public class RespBackpackInfoDto {

    private List<RespBackpackItemDto> items;

    private int capacity;

    public List<RespBackpackItemDto> getItems() {
        return items;
    }

    public void setItems(List<RespBackpackItemDto> items) {
        this.items = items;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
