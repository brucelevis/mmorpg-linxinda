package com.wan37.event;

import com.wan37.logic.player.Player;

public class LeagueJoinEvent {

    public LeagueJoinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
