package com.wan37.logic.npc.service;

import com.wan37.config.ConfigLoader;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.NpcTalkEvent;
import com.wan37.logic.mission.MissionCanAcceptListGetter;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.encode.MissionEncoder;
import com.wan37.logic.mission.PlayerMission;
import com.wan37.logic.npc.config.NpcCfg;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class NpcTalkExec {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private MissionCanAcceptListGetter missionCanAcceptListGetter;

    @Autowired
    private MissionEncoder missionEncoder;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void exec(Player player, Integer npcId) {
        NpcCfg npcCfg = configLoader.load(NpcCfg.class, npcId).orElse(null);
        if (npcCfg == null) {
            player.syncClient("找不到对应Npc配置表");
            return;
        }

        // 抛出NPC对话事件
        generalEventListenersManager.fireEvent(new NpcTalkEvent(npcId, player));

        //TODO: 检查场景npc

        // 可接受的该Npc的任务
        List<MissionCfg> canAcceptList = missionCanAcceptListGetter.get(player.getLevel(), player.getMission()).stream()
                .filter(c -> Objects.equals(c.getNpcId(), npcId))
                .collect(Collectors.toList());

        // 正在进行的该Npc的任务
        List<PlayerMission> proceedingList = player.getMission().getProceedingList().stream()
                .filter(m -> Objects.equals(m.getMissionCfg().getNpcId(), npcId))
                .collect(Collectors.toList());

        if (canAcceptList.isEmpty() && proceedingList.isEmpty()) {
            // 没任务
            String msg = String.format("【%s】 %s", npcCfg.getName(), npcCfg.getTalk());
            player.syncClient(msg);
            return;
        }

        if (!canAcceptList.isEmpty()) {
            String msg = canAcceptList.stream()
                    .map(c -> missionEncoder.encode(c, false))
                    .collect(Collectors.joining("\n----------------------------------------\n"));
            player.syncClient(msg);
        }

        if (!proceedingList.isEmpty()) {
            String msg = proceedingList.stream()
                    .map(m -> missionEncoder.encode(m.getMissionCfg(), m.canComplete()))
                    .collect(Collectors.joining("\n----------------------------------------\n"));
            player.syncClient(msg);
        }
    }
}
