package com.wan37.logic.trade;

import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.player.Player;

import java.util.Map;

/**
 * 交易中的玩家信息
 *
 * @author linda
 */
public class TradePlayer {

    private Player player;

    /**
     * key：交易窗口格子编号，value：交易窗口格子
     */
    private Map<Integer, ItemDb> items;

    /**
     * key：虚物id，value：数量
     */
    private Map<Integer, Long> currency;

    /**
     * 是否确认提交
     */
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
