package com.wan37.logic.mission.service;

import com.wan37.logic.mission.MissionCanAcceptListGetter;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.encode.MissionEncoder;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MissionInfoExec {

    private static final String SEPARATOR = "\n-----------------------------------------------\n";

    @Autowired
    private MissionCfgLoader missionCfgLoader;

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

        return head + canAcceptList + proceedingList + completeList;
    }

    private String encodeCanAcceptList(int playerLv, IMission iMission) {
        String head = "->可领取列表：\n";

        // 等级达到，前置完成，没做过且不是正在进行
        String list = missionCanAcceptListGetter.get(playerLv, iMission).stream()
                .map(c -> missionEncoder.encode(c))
                .collect(Collectors.joining(SEPARATOR));

        return head + list;
    }

    private String encodeProceedingList(IMission iMission) {
        String head = "\n->正在进行列表：\n";
        String list = iMission.getProceedingList().stream()
                .map(this::encodePlayerMission)
                .collect(Collectors.joining(SEPARATOR));
        return head + list;
    }

    private String encodeCompleteList(IMission iMission) {
        String head = "\n->已完成列表：\n";
        String list = iMission.getCompleteList().stream()
                .map(this::encodePlayerMission)
                .collect(Collectors.joining(SEPARATOR));
        return head + list;
    }

    private String encodePlayerMission(IPlayerMission playerMission) {
        MissionCfg missionCfg = missionCfgLoader.load(playerMission.getMissionId())
                .orElseThrow(() -> new RuntimeException("任务配置表错误"));

        return missionEncoder.encode(missionCfg);
    }
}
