package com.wan37.logic.npc.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.mission.MissionCanAcceptListGetter;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.encode.MissionEncoder;
import com.wan37.logic.npc.config.NpcCfg;
import com.wan37.logic.npc.config.NpcCfgLoader;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NpcTalkExec {

    @Autowired
    private NpcCfgLoader npcCfgLoader;

    @Autowired
    private MissionCanAcceptListGetter missionCanAcceptListGetter;

    @Autowired
    private MissionEncoder missionEncoder;

    public void exec(Player player, Integer npcId) {
        NpcCfg npcCfg = npcCfgLoader.load(npcId)
                .orElseThrow(() -> new GeneralErrorExecption("找不到对应Npc配置表"));

        //TODO: 检查场景npc

        List<MissionCfg> npcMission = missionCanAcceptListGetter.get(player.getLevel(), player.getMission()).stream()
                .filter(c -> Objects.equals(c.getNpcId(), npcId))
                .collect(Collectors.toList());

        if (npcMission.isEmpty()) {
            // 没任务
            player.syncClient(npcCfg.getTalk());
            return;
        }

        String msg = npcMission.stream()
                .map(c -> missionEncoder.encode(c))
                .collect(Collectors.joining("\n----------------------------------------\n"));
        player.syncClient(msg);
    }
}
