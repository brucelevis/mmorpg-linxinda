package com.wan37.logic.player;

import com.wan37.common.GeneralResponseDto;

public interface IPlayer {

    Long getUid();

    Integer getSceneId();

    void setSceneId(Integer sceneId);

    String getName();

    Integer getFactionId();

    int getLevel();

    void syncClient(GeneralResponseDto dto);

    void syncClient(String content);
}
