package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 加入组队事件
 *
 * @author linda
 */
public class TeamJoinEvent {

    public TeamJoinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
