package com.wan37.event;

import com.wan37.logic.player.Player;

public class NpcTalkEvent {

    public NpcTalkEvent(Integer npcId, Player player) {
        this.npcId = npcId;
        this.player = player;
    }

    public Integer getNpcId() {
        return npcId;
    }

    public Player getPlayer() {
        return player;
    }

    private final Integer npcId;
    private final Player player;
}
