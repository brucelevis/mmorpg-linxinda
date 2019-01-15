package com.wan37.event.event;

import com.wan37.logic.player.Player;

/**
 * npc对话事件
 *
 * @author linda
 */
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
