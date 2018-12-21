package com.wan37.logic.mission;

import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.entity.IMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionCanAcceptListGetter {

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    public List<MissionCfg> get(int playerLv, IMission iMission) {
        return missionCfgLoader.loads().stream()
                .filter(cfg -> playerLv >= cfg.getLevel())
                .filter(cfg -> checkPreMission(cfg, iMission))
                .filter(cfg -> !iMission.isProceeding(cfg.getId()))
                .filter(cfg -> !iMission.hadCompleted(cfg.getId()))
                .collect(Collectors.toList());
    }

    private boolean checkPreMission(MissionCfg missionCfg, IMission iMission) {
        if (missionCfg.getPreId() == null) {
            return true;
        }

        return iMission.hadCompleted(missionCfg.getPreId());
    }
}
