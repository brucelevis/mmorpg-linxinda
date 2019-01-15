package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 升级事件
 *
 * @author linda
 */
public class LevelUpEvent {

    public LevelUpEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
