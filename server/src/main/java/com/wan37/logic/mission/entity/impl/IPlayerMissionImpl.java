package com.wan37.logic.mission.entity.impl;

import com.wan37.logic.mission.database.PlayerMissionDb;
import com.wan37.logic.mission.entity.IPlayerMission;

class IPlayerMissionImpl implements IPlayerMission {

    public IPlayerMissionImpl(PlayerMissionDb playerMissionDb) {
        this.playerMissionDb = playerMissionDb;
    }

    @Override
    public PlayerMissionDb getPlayerMission() {
        return playerMissionDb;
    }

    @Override
    public long getCompleteTime() {
        return playerMissionDb.getCompleteTime();
    }

    @Override
    public Integer getMissionId() {
        return playerMissionDb.getMissionId();
    }

    private final PlayerMissionDb playerMissionDb;
}
