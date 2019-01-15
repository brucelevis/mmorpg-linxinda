package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 离线事件
 *
 * @author linda
 */
public class OfflineEvent {

    public OfflineEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
