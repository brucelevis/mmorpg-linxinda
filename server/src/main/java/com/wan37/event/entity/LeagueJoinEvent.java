package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 加入公会事件
 *
 * @author linda
 */
public class LeagueJoinEvent {

    public LeagueJoinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
