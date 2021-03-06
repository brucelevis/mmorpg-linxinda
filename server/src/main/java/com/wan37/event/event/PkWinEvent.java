package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 决斗胜利事件
 *
 * @author linda
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
