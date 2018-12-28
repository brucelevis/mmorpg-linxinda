package com.wan37.event.entity;

import com.wan37.logic.player.Player;

public class VirtualItemAddEvent {

    public VirtualItemAddEvent(Integer cfgId, long amount, Player player) {
        this.cfgId = cfgId;
        this.amount = amount;
        this.player = player;
    }

    public Integer getCfgId() {
        return cfgId;
    }

    public long getAmount() {
        return amount;
    }

    public Player getPlayer() {
        return player;
    }

    private final Integer cfgId;
    private final long amount;
    private final Player player;
}
