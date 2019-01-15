package com.wan37.logic.mission.impl;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.database.PlayerMissionDb;
import com.wan37.logic.mission.PlayerMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class PlayerMissionFactoryImpl implements PlayerMission.Factory {

    @Autowired
    private ConfigLoader configLoader;

    @Override
    public PlayerMission create(PlayerMissionDb playerMissionDb) {
        MissionCfg missionCfg = configLoader.load(MissionCfg.class, playerMissionDb.getMissionId())
                .orElseThrow(() -> new RuntimeException("找不到任务配置表"));

        return new PlayerMissionImpl(playerMissionDb, missionCfg);
    }
}
