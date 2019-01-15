package com.wan37.logic.mission.impl;

import com.wan37.logic.mission.database.MissionDb;
import com.wan37.logic.mission.Mission;
import com.wan37.logic.mission.PlayerMission;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class MissionImpl implements Mission {

    public MissionImpl(MissionDb missionDb, Map<Integer, PlayerMission> missionMap) {
        this.missionDb = missionDb;
        this.missionMap = missionMap;
    }

    @Override
    public boolean hadCompleted(Integer missionId) {
        PlayerMission playerMission = missionMap.get(missionId);
        return playerMission != null && playerMission.getCompleteTime() != 0;
    }

    @Override
    public boolean isProceeding(Integer missionId) {
        PlayerMission playerMission = missionMap.get(missionId);
        return playerMission != null && playerMission.getCompleteTime() == 0;
    }

    @Override
    public void acceptMission(PlayerMission playerMission) {
        missionMap.put(playerMission.getMissionId(), playerMission);
        missionDb.getMissions().add(playerMission.getPlayerMission());
    }

    @Override
    public List<PlayerMission> getCompleteList() {
        return missionMap.values().stream()
                .filter(m -> hadCompleted(m.getMissionId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlayerMission> getProceedingList() {
        return missionMap.values().stream()
                .filter(m -> isProceeding(m.getMissionId()))
                .collect(Collectors.toList());
    }

    @Override
    public PlayerMission getPlayerMission(Integer missionId) {
        return missionMap.get(missionId);
    }

    private final MissionDb missionDb;
    private final Map<Integer, PlayerMission> missionMap;
}
