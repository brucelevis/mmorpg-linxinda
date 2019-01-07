package com.wan37.event.entity;

import com.wan37.logic.player.Player;

/**
 * 副本完成事件
 *
 * @author linda
 */
public class DungeonCompleteEvent {

    public DungeonCompleteEvent(Player player, Integer dungeonCfgId) {
        this.player = player;
        this.dungeonCfgId = dungeonCfgId;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getDungeonCfgId() {
        return dungeonCfgId;
    }

    private final Player player;
    private final Integer dungeonCfgId;
}
