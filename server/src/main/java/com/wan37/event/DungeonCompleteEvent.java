package com.wan37.event;

import com.wan37.logic.player.Player;

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
