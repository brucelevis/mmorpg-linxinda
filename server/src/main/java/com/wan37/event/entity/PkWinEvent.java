package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 决斗胜利事件
 */
public class PkWinEvent {

    public PkWinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
