package com.wan37.logic.mission.service.accept;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.entity.Mission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class MissionAcceptExec {

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    @Autowired
    private MissionAccepter missionAccepter;

    public void exec(Player player, Integer missionId) {
        MissionCfg missionCfg = missionCfgLoader.load(missionId)
                .orElseThrow(() -> new GeneralErrorException("找不到对应的任务配置表"));

        if (player.getLevel() < missionCfg.getLevel()) {
            throw new GeneralErrorException("等级不足");
        }

        Mission mission = player.getMission();
        if (mission.hadCompleted(missionId)) {
            throw new GeneralErrorException("任务已经完成");
        }

        if (mission.isProceeding((missionId))) {
            throw new GeneralErrorException("正在进行的任务");
        }

        Integer preId = missionCfg.getPreId();
        if (preId != null && !mission.hadCompleted(preId)) {
            throw new GeneralErrorException("前置任务未完成");
        }

        missionAccepter.accept(player, missionCfg);
    }
}
