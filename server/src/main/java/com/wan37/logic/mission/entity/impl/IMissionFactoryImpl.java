package com.wan37.logic.mission.entity.impl;

import com.wan37.logic.mission.database.MissionDb;
import com.wan37.logic.mission.entity.IMission;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IMissionFactoryImpl implements IMission.Factory {

    @Override
    public IMission create(MissionDb missionDb) {
        return new IMissionImpl(missionDb, new HashMap<>());
    }
}
