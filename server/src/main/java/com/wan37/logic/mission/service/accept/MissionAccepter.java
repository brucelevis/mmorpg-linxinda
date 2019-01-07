package com.wan37.logic.mission.service.accept;

import com.wan37.logic.mission.complete.MissionCompleteChecker;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.database.PlayerMissionDb;
import com.wan37.logic.mission.entity.PlayerMission;
import com.wan37.logic.player.Player;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 接受任务处理
 *
 * @author linda
 */
@Service
public class MissionAccepter {

    @Autowired
    private PlayerMission.Factory playerMissionFactory;

    @Autowired
    private MissionCompleteChecker missionCompleteChecker;

    public void accept(Player player, MissionCfg missionCfg) {
        PlayerMission playerMission = createPlayerMission(player.getUid(), missionCfg.getId());
        player.getMission().acceptMission(playerMission);

        String msg = String.format("你领取了[%s]任务", missionCfg.getName());
        player.syncClient(msg);

        // 任务完成检查
        missionCompleteChecker.check(player, playerMission);
    }

    private PlayerMission createPlayerMission(Long playerUid, Integer missionId) {
        PlayerMissionDb playerMissionDb = createPlayerMissionDb(playerUid, missionId);
        return playerMissionFactory.create(playerMissionDb);
    }

    private PlayerMissionDb createPlayerMissionDb(Long playerUid, Integer missionId) {
        PlayerMissionDb db = new PlayerMissionDb();
        db.setId(IdUtil.generate());
        db.setMissionId(missionId);
        db.setPlayerUid(playerUid);
        db.setCanComplete(false);
        db.setAcceptTime(DateTimeUtils.toEpochMilli(LocalDateTime.now()));
        return db;
    }
}
