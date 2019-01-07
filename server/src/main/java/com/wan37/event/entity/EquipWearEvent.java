package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 装备穿戴事件
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
