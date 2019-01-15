package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 添加好友事件
 *
 * @author linda
 */
public class FriendAddEvent {

    public FriendAddEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
