package com.wan37.logic.mission.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.database.PlayerMissionDb;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MissionAcceptExec {

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    @Autowired
    private IPlayerMission.Factory playerMissionFactory;

    @Autowired
    private IdTool idTool;

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

        IPlayerMission playerMission = createPlayerMission(player.getUid(), missionId);
        iMission.acceptMission(playerMission);

        String msg = String.format("你领取了[%s]任务", missionCfg.getName());
        player.syncClient(msg);
    }

    private IPlayerMission createPlayerMission(Long playerUid, Integer missionId) {
        PlayerMissionDb playerMissionDb = createPlayerMissionDb(playerUid, missionId);
        return playerMissionFactory.create(playerMissionDb);
    }

    private PlayerMissionDb createPlayerMissionDb(Long playerUid, Integer missionId) {
        PlayerMissionDb db = new PlayerMissionDb();
        db.setId(idTool.generate());
        db.setMissionId(missionId);
        db.setPlayerUid(playerUid);
        db.setAcceptTime(DateTimeUtils.toEpochMilli(LocalDateTime.now()));
        return db;
    }
}
