package com.wan37.logic.mission.impl;

import com.wan37.logic.mission.database.MissionDb;
import com.wan37.logic.mission.Mission;
import com.wan37.logic.mission.PlayerMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class MissionFactoryImpl implements Mission.Factory {

    @Autowired
    private PlayerMission.Factory playerMissionFactory;

    @Override
    public Mission create(MissionDb missionDb) {
        Map<Integer, PlayerMission> missionMap = missionDb.getMissions().stream()
                .map(m -> playerMissionFactory.create(m))
                .collect(Collectors.toMap(PlayerMission::getMissionId, Function.identity()));

        return new MissionImpl(missionDb, missionMap);
    }
}
