package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 离开场景事件
 *
 * @author linda
 */
public class SceneLeaveEvent {

    public SceneLeaveEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
