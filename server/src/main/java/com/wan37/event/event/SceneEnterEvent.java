package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 进入场景事件
 *
 * @author linda
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
