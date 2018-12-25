package com.wan37.event;

import com.wan37.logic.player.Player;

public class TeamJoinEvent {

    public TeamJoinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}