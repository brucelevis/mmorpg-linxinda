package com.wan37.logic.mission.entity.impl;

import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.database.PlayerMissionDb;
import com.wan37.logic.mission.entity.PlayerMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class PlayerMissionFactoryImpl implements PlayerMission.Factory {

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    @Override
    public PlayerMission create(PlayerMissionDb playerMissionDb) {
        MissionCfg missionCfg = missionCfgLoader.load(playerMissionDb.getMissionId())
                .orElseThrow(() -> new RuntimeException("找不到任务配置表"));

        return new PlayerMissionImpl(playerMissionDb, missionCfg);
    }
}
