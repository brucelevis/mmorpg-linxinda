package com.wan37.logic.mission.entity.impl;

import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.database.PlayerMissionDb;
import com.wan37.logic.mission.entity.IPlayerMission;

class IPlayerMissionImpl implements IPlayerMission {

    public IPlayerMissionImpl(PlayerMissionDb playerMissionDb, MissionCfg missionCfg) {
        this.playerMissionDb = playerMissionDb;
        this.missionCfg = missionCfg;
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
    public void setCompleteTime(long time) {
        playerMissionDb.setCompleteTime(time);
    }

    @Override
    public Integer getMissionId() {
        return playerMissionDb.getMissionId();
    }

    @Override
    public MissionCfg getMissionCfg() {
        return missionCfg;
    }

    @Override
    public boolean canComplete() {
        return playerMissionDb.isCanComplete();
    }

    @Override
    public void setCanComplete(boolean canComplete) {
        playerMissionDb.setCanComplete(canComplete);
    }

    @Override
    public int getProgress() {
        return playerMissionDb.getProgress();
    }

    @Override
    public void setProgress(int progress) {
        playerMissionDb.setProgress(progress);
    }

    private final PlayerMissionDb playerMissionDb;
    private final MissionCfg missionCfg;
}
