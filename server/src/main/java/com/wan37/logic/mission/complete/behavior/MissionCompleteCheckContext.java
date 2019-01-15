package com.wan37.logic.mission.complete.behavior;

import com.wan37.logic.mission.PlayerMission;
import com.wan37.logic.player.Player;

/**
 * 任务完成检查上下文
 *
 * @author linda
 */
public class MissionCompleteCheckContext {

    public MissionCompleteCheckContext(Player player, PlayerMission playerMission) {
        this.player = player;
        this.playerMission = playerMission;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerMission getPlayerMission() {
        return playerMission;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    private final Player player;
    private final PlayerMission playerMission;

    private boolean result;
}
