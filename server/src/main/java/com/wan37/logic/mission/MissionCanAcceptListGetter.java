package com.wan37.logic.mission;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.entity.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取玩家可接受的任务列表
 *
 * @author linda
 */
@Service
public class MissionCanAcceptListGetter {

    @Autowired
    private ConfigLoader configLoader;

    public List<MissionCfg> get(int playerLv, Mission mission) {
        return configLoader.loads(MissionCfg.class).stream()
                .filter(cfg -> playerLv >= cfg.getLevel())
                .filter(cfg -> checkPreMission(cfg, mission))
                .filter(cfg -> !mission.isProceeding(cfg.getId()))
                .filter(cfg -> !mission.hadCompleted(cfg.getId()))
                .collect(Collectors.toList());
    }

    private boolean checkPreMission(MissionCfg missionCfg, Mission mission) {
        if (missionCfg.getPreId() == null) {
            return true;
        }

        return mission.hadCompleted(missionCfg.getPreId());
    }
}
