package com.wan37.logic.mission.complete.behavior;

import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;

public class MissionCompleteCheckContext {

    public MissionCompleteCheckContext(Player player, IPlayerMission playerMission) {
        this.player = player;
        this.playerMission = playerMission;
    }

    public Player getPlayer() {
        return player;
    }

    public IPlayerMission getPlayerMission() {
        return playerMission;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    private final Player player;
    private final IPlayerMission playerMission;

    private boolean result;
}
