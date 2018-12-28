package com.wan37.event.entity;

import com.wan37.logic.player.Player;

public class PkWinEvent {

    public PkWinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
