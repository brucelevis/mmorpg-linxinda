package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 登录事件
 *
 * @author linda
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
