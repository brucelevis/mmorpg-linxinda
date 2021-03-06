package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * 装备穿戴事件
 *
 * @author linda
 */
public class EquipWearEvent {

    public EquipWearEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}
