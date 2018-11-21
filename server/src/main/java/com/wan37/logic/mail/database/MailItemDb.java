package com.wan37.logic.mail.database;

import com.wan37.logic.backpack.database.ItemDb;

import java.util.List;

public class MailItemDb {

    private List<ItemDb> items;

    public List<ItemDb> getItems() {
        return items;
    }

    public void setItems(List<ItemDb> items) {
        this.items = items;
    }
}
