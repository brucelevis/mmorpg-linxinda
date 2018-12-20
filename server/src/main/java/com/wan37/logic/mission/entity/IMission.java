package com.wan37.logic.mission.entity;

import com.wan37.logic.mission.database.MissionDb;

import java.util.List;

public interface IMission {

    interface Factory {

        IMission create(MissionDb missionDb);
    }

    boolean hadCompleted(Integer missionId);

    boolean isProceeding(Integer missionId);

    void acceptMission(IPlayerMission playerMission);

    List<IPlayerMission> getCompleteList();

    List<IPlayerMission> getProceedingList();
}
