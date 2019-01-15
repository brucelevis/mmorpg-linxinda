package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 交易成功事件
 *
 * @author linda
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
