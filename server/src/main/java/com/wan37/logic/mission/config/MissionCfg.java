package com.wan37.logic.mission.config;

import java.util.List;

public interface MissionCfg {

    Integer getId();

    int getLevel();

    Integer getPreId();

    Integer getNextId();

    String getName();

    String getDesc();

    String getDetail();

    String getCompleteTip();

    String getProceedTip();

    long getExp();

    List<MissionRewardCfg> getReward();

    Integer getNpcId();

    Integer getSceneId();

    Integer getType();

    Integer getTargetId();

    int getArgs();
}
