package com.wan37.logic.mission.entity.impl;

import com.wan37.logic.mission.database.MissionDb;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.mission.entity.IPlayerMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IMissionFactoryImpl implements IMission.Factory {

    @Autowired
    private IPlayerMission.Factory playerMissionFactory;

    @Override
    public IMission create(MissionDb missionDb) {
        Map<Integer, IPlayerMission> missionMap = missionDb.getMissions().stream()
                .map(m -> playerMissionFactory.create(m))
                .collect(Collectors.toMap(IPlayerMission::getMissionId, Function.identity()));

        return new IMissionImpl(missionDb, missionMap);
    }
}
