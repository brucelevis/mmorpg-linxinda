package com.wan37.logic.mission.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.mission.MissionCanAcceptListGetter;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.encode.MissionEncoder;
import com.wan37.logic.mission.Mission;
import com.wan37.logic.mission.PlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class MissionInfoExec {

    private static final String SEPARATOR = "\n-----------------------------------------------\n";

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private MissionEncoder missionEncoder;

    @Autowired
    private MissionCanAcceptListGetter missionCanAcceptListGetter;

    public void exec(Player player) {
        String msg = encodeMissions(player);
        player.syncClient(msg);
    }

    private String encodeMissions(Player player) {
        String head = "################ 玩家任务信息如下 ################\n";
        String canAcceptList = encodeCanAcceptList(player.getLevel(), player.getMission());
        String proceedingList = encodeProceedingList(player.getMission());
        String completeList = encodeCompleteList(player.getMission());

        return head + canAcceptList + "\n"
                + proceedingList + "\n"
                + completeList;
    }

    private String encodeCanAcceptList(int playerLv, Mission mission) {
        String head = "->可领取列表：\n";

        // 等级达到，前置完成，没做过且不是正在进行
        String list = missionCanAcceptListGetter.get(playerLv, mission).stream()
                .map(c -> missionEncoder.encode(c, false))
                .collect(Collectors.joining(SEPARATOR));

        return head + list;
    }

    private String encodeProceedingList(Mission mission) {
        String head = "\n->正在进行列表：\n";
        String list = mission.getProceedingList().stream()
                .map(this::encodePlayerMission)
                .collect(Collectors.joining(SEPARATOR));
        return head + list;
    }

    private String encodeCompleteList(Mission mission) {
        String head = "\n->已完成列表：\n";
        String list = mission.getCompleteList().stream()
                .map(this::encodePlayerMission)
                .collect(Collectors.joining(SEPARATOR));
        return head + list;
    }

    private String encodePlayerMission(PlayerMission playerMission) {
        MissionCfg missionCfg = configLoader.load(MissionCfg.class, playerMission.getMissionId())
                .orElseThrow(() -> new RuntimeException("任务配置表错误"));

        return missionEncoder.encode(missionCfg, playerMission.canComplete());
    }
}
