package com.wan37.logic.league.service.get.item;

import java.util.List;

public interface ReqLGetItem {

    interface Factory {

        ReqLGetItem create(String args);
    }

    List<LGetItem> getItems();
}
