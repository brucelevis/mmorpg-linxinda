package com.wan37.logic.mission.entity.impl;

import com.wan37.logic.mission.database.MissionDb;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.mission.entity.IPlayerMission;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class IMissionImpl implements IMission {

    public IMissionImpl(MissionDb missionDb, Map<Integer, IPlayerMission> missionMap) {
        this.missionDb = missionDb;
        this.missionMap = missionMap;
    }

    @Override
    public boolean hadCompleted(Integer missionId) {
        IPlayerMission playerMission = missionMap.get(missionId);
        return playerMission != null && playerMission.getCompleteTime() != 0;
    }

    @Override
    public boolean isProceeding(Integer missionId) {
        IPlayerMission playerMission = missionMap.get(missionId);
        return playerMission != null && playerMission.getCompleteTime() == 0;
    }

    @Override
    public void acceptMission(IPlayerMission playerMission) {
        missionMap.put(playerMission.getMissionId(), playerMission);
        missionDb.getMissions().add(playerMission.getPlayerMission());
    }

    @Override
    public List<IPlayerMission> getCompleteList() {
        return missionMap.values().stream()
                .filter(m -> hadCompleted(m.getMissionId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IPlayerMission> getProceedingList() {
        return missionMap.values().stream()
                .filter(m -> isProceeding(m.getMissionId()))
                .collect(Collectors.toList());
    }

    private final MissionDb missionDb;
    private final Map<Integer, IPlayerMission> missionMap;
}
