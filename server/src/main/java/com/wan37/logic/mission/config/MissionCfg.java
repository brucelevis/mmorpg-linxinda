package com.wan37.logic.mission.config;

import java.util.List;
import java.util.Set;

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

    int getArgsAsInt();

    Set<Integer> getArgsAsIntSet();

    boolean isAutoCommit();

    boolean isAutoAccept();
}
