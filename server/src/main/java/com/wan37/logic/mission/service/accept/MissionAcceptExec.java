package com.wan37.logic.mission.service.accept;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.Mission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class MissionAcceptExec {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private MissionAccepter missionAccepter;

    public void exec(Player player, Integer missionId) {
        MissionCfg missionCfg = configLoader.load(MissionCfg.class, missionId).orElse(null);
        if (missionCfg == null) {
            player.syncClient("找不到对应的任务配置表");
            return;
        }

        if (player.getLevel() < missionCfg.getLevel()) {
            player.syncClient("等级不足");
            return;
        }

        Mission mission = player.getMission();
        if (mission.hadCompleted(missionId)) {
            player.syncClient("任务已经完成");
            return;
        }

        if (mission.isProceeding((missionId))) {
            player.syncClient("正在进行的任务");
            return;
        }

        Integer preId = missionCfg.getPreId();
        if (preId != null && !mission.hadCompleted(preId)) {
            player.syncClient("前置任务未完成");
            return;
        }

        missionAccepter.accept(player, missionCfg);
    }
}
