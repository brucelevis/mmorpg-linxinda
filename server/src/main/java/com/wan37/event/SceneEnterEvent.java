package com.wan37.event;

public class SceneEnterEvent {

    public SceneEnterEvent(Long playerUid) {
        this.playerUid = playerUid;
    }

    public Long getPlayerUid() {
        return playerUid;
    }

    private final Long playerUid;
}
