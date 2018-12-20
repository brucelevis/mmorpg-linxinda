package com.wan37.logic.mission.entity;

import com.wan37.logic.mission.database.PlayerMissionDb;

public interface IPlayerMission {

    interface Factory {

        IPlayerMission create(PlayerMissionDb playerMissionDb);
    }

    PlayerMissionDb getPlayerMission();

    long getCompleteTime();

    Integer getMissionId();
}
