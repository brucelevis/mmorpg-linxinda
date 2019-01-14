package com.wan37.logic.mission.encode;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.mission.config.MissionCfg;
import com.wan37.logic.mission.config.MissionRewardCfg;
import com.wan37.logic.npc.config.NpcCfg;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.scene.config.SceneCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 任务信息编码
 *
 * @author linda
 */
@Service
public class MissionEncoder {

    @Autowired
    private ConfigLoader configLoader;

    @Deprecated
    public String encode(MissionCfg missionCfg, boolean canComplete) {
        //FIXME: 代码整洁之道：传布尔值意味着做了不止一件事。
        return String.format("%s任务（id：%s）：%s（%s：%s）\n描述：%s\n完成条件：%s\n奖励：%s，exp×%s",
                canComplete ? "（可完成） " : "", missionCfg.getId(), missionCfg.getName(),
                configLoader.loadName(SceneCfg.class, missionCfg.getSceneId()), configLoader.loadName(NpcCfg.class, missionCfg.getNpcId()),
                missionCfg.getDesc(), missionCfg.getDetail(), encodeReward(missionCfg), missionCfg.getExp());
    }

    private String encodeReward(MissionCfg missionCfg) {
        return missionCfg.getReward().stream()
                .map(this::encodeRewardItem)
                .collect(Collectors.joining("，"));
    }

    private String encodeRewardItem(MissionRewardCfg missionRewardCfg) {
        Integer cfgId = missionRewardCfg.getId();

        //FIXME: 写死虚物id < 200
        return String.format("%s×%s", cfgId < 200 ? configLoader.loadName(VirtualItemCfg.class, cfgId) :
                configLoader.loadName(PropsCfg.class, cfgId), missionRewardCfg.getAmount());
    }
}
