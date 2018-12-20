package com.wan37.logic.mission.service;

import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionCfgLoader;
import com.wan37.logic.mission.config.MissionRewardCfg;
import com.wan37.logic.mission.entity.IMission;
import com.wan37.logic.mission.entity.IPlayerMission;
import com.wan37.logic.npc.config.NpcCfgLoader;
import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import com.wan37.logic.scene.config.SceneCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MissionInfoExec {

    private static final String SEPARATOR = "\n-----------------------------------------------\n";

    @Autowired
    private MissionCfgLoader missionCfgLoader;

    @Autowired
    private NpcCfgLoader npcCfgLoader;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

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
        String list = missionCfgLoader.loads().stream()
                .filter(cfg -> playerLv >= cfg.getLevel())
                .filter(cfg -> checkPreMission(cfg, iMission))
                .filter(cfg -> !iMission.isProceeding(cfg.getId()))
                .filter(cfg -> !iMission.hadCompleted(cfg.getId()))
                .map(this::encodeMission)
                .collect(Collectors.joining(SEPARATOR));

        return head + list;
    }

    private boolean checkPreMission(MissionCfg missionCfg, IMission iMission) {
        if (missionCfg.getPreId() == null) {
            return true;
        }

        return iMission.hadCompleted(missionCfg.getPreId());
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

        return encodeMission(missionCfg);
    }

    private String encodeMission(MissionCfg missionCfg) {
        return String.format("任务：%s（%s：%s）\n描述：%s\n奖励：%s，exp×%s", missionCfg.getName(),
                sceneCfgLoader.loadName(missionCfg.getSceneId()), npcCfgLoader.loadName(missionCfg.getNpcId()),
                missionCfg.getDesc(), encodeReward(missionCfg), missionCfg.getExp());
    }

    private String encodeReward(MissionCfg missionCfg) {
        return missionCfg.getReward().stream()
                .map(this::encodeRewardItem)
                .collect(Collectors.joining(","));
    }

    private String encodeRewardItem(MissionRewardCfg missionRewardCfg) {
        Integer cfgId = missionRewardCfg.getId();

        //FIXME: 写死虚物id < 200
        return String.format("%s×%s", cfgId < 200 ? virtualItemCfgLoader.getName(cfgId) :
                propsCfgLoader.getName(cfgId), missionRewardCfg.getAmount());
    }
}
