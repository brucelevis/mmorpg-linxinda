package com.wan37.logic.mail.database;

import com.wan37.logic.backpack.database.ItemDb;

import java.util.List;

/**
 * 玩家邮件道具附件数据库实体类
 *
 * @author linda
 */
public class MailItemDb {

    private List<ItemDb> items;

    public List<ItemDb> getItems() {
        return items;
    }

    public void setItems(List<ItemDb> items) {
        this.items = items;
    }
}
