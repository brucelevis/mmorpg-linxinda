package com.wan37.logic.mission.encode;

import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionRewardCfg;
import com.wan37.logic.npc.config.NpcCfgLoader;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.config.VirtualItemCfgLoader;
import com.wan37.logic.scene.config.SceneCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MissionEncoder {

    @Autowired
    private NpcCfgLoader npcCfgLoader;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private VirtualItemCfgLoader virtualItemCfgLoader;

    @Autowired
    private PropsCfgLoader propsCfgLoader;

    public String encode(MissionCfg missionCfg) {
        return String.format("任务（id：%s）：%s（%s：%s）\n描述：%s\n奖励：%s，exp×%s", missionCfg.getId(), missionCfg.getName(),
                sceneCfgLoader.loadName(missionCfg.getSceneId()), npcCfgLoader.loadName(missionCfg.getNpcId()),
                missionCfg.getDesc(), encodeReward(missionCfg), missionCfg.getExp());
    }

    private String encodeReward(MissionCfg missionCfg) {
        return missionCfg.getReward().stream()
                .map(this::encodeRewardItem)
                .collect(Collectors.joining("，"));
    }

    private String encodeRewardItem(MissionRewardCfg missionRewardCfg) {
        Integer cfgId = missionRewardCfg.getId();

        //FIXME: 写死虚物id < 200
        return String.format("%s×%s", cfgId < 200 ? virtualItemCfgLoader.getName(cfgId) :
                propsCfgLoader.getName(cfgId), missionRewardCfg.getAmount());
    }
}
