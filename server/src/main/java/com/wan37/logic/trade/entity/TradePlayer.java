package com.wan37.logic.trade.entity;

import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.player.Player;

import java.util.Map;

public class TradePlayer {

    private Player player;
    private Map<Integer, ItemDb> items;
    private Map<Integer, Long> currency;
    private boolean commit;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map<Integer, ItemDb> getItems() {
        return items;
    }

    public void setItems(Map<Integer, ItemDb> items) {
        this.items = items;
    }

    public Map<Integer, Long> getCurrency() {
        return currency;
    }

    public void setCurrency(Map<Integer, Long> currency) {
        this.currency = currency;
    }

    public boolean isCommit() {
        return commit;
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }
}
