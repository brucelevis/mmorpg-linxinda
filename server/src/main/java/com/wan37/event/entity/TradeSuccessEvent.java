package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 交易成功事件
 */
public class TradeSuccessEvent {

    public TradeSuccessEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
