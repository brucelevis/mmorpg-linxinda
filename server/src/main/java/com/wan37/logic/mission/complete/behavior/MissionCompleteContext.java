package com.wan37.logic.mission.complete.behavior;

import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;

public class MissionCompleteContext {

    public MissionCompleteContext(Player player, IPlayerMission playerMission) {
        this.player = player;
        this.playerMission = playerMission;
    }

    public Player getPlayer() {
        return player;
    }

    public IPlayerMission getPlayerMission() {
        return playerMission;
    }

    private final Player player;
    private final IPlayerMission playerMission;
}
