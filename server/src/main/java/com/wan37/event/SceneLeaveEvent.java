package com.wan37.event;

public class SceneLeaveEvent {

    public SceneLeaveEvent(Integer sceneId, Long playerUid) {
        this.sceneId = sceneId;
        this.playerUid = playerUid;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public Long getPlayerUid() {
        return playerUid;
    }

    private final Integer sceneId;
    private final Long playerUid;
}
