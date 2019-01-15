package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 任务完成事件
 *
 * @author linda
 */
public class MissionCompleteEvent {

    public MissionCompleteEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
