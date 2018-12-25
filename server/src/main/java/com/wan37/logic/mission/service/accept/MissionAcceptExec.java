package com.wan37.logic.mission.service.accept;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionAcceptExec {

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    @Autowired
    private MissionAccepter missionAccepter;

    public void exec(Player player, Integer missionId) {
        MissionCfg missionCfg = missionCfgLoader.load(missionId)
                .orElseThrow(() -> new GeneralErrorExecption("找不到对应的任务配置表"));

        if (player.getLevel() < missionCfg.getLevel()) {
            throw new GeneralErrorExecption("等级不足");
        }

        IMission iMission = player.getMission();
        if (iMission.hadCompleted(missionId)) {
            throw new GeneralErrorExecption("任务已经完成");
        }

        if (iMission.isProceeding((missionId))) {
            throw new GeneralErrorExecption("正在进行的任务");
        }

        Integer preId = missionCfg.getPreId();
        if (preId != null && !iMission.hadCompleted(preId)) {
            throw new GeneralErrorExecption("前置任务未完成");
        }

        missionAccepter.accept(player, missionCfg);
    }
}