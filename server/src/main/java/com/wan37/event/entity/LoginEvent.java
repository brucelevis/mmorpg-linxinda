package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 登录事件
 */
public class LoginEvent {

    public LoginEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
