package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 进入场景事件
 */
public class SceneEnterEvent {

    public SceneEnterEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
