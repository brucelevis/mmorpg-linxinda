package com.wan37.logic.mission.entity.impl;

import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.database.PlayerMissionDb;
import com.wan37.logic.mission.entity.IPlayerMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPlayerMissionFactoryImpl implements IPlayerMission.Factory {

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    @Override
    public IPlayerMission create(PlayerMissionDb playerMissionDb) {
        MissionCfg missionCfg = missionCfgLoader.load(playerMissionDb.getMissionId())
                .orElseThrow(() -> new RuntimeException("找不到任务配置表"));

        return new IPlayerMissionImpl(playerMissionDb, missionCfg);
    }
}
