package com.wan37.logic.mission.entity.impl;

import com.wan37.logic.mission.database.PlayerMissionDb;
import com.wan37.logic.mission.entity.IPlayerMission;
import org.springframework.stereotype.Service;

@Service
public class IPlayerMissionFactoryImpl implements IPlayerMission.Factory {

    @Override
    public IPlayerMission create(PlayerMissionDb playerMissionDb) {
        return new IPlayerMissionImpl(playerMissionDb);
    }
}
