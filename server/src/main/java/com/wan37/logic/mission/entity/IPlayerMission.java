package com.wan37.logic.mission.entity;

import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.database.PlayerMissionDb;

public interface IPlayerMission {

    interface Factory {

        IPlayerMission create(PlayerMissionDb playerMissionDb);
    }

    PlayerMissionDb getPlayerMission();

    long getCompleteTime();

    void setCompleteTime(long time);

    Integer getMissionId();

    MissionCfg getMissionCfg();

    boolean canComplete();

    void setCanComplete(boolean canComplete);

    int getProgress();

    void setProgress(int progress);
}
