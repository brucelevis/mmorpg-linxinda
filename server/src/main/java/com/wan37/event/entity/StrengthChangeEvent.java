package com.wan37.event.entity;

public class StrengthChangeEvent {

    public StrengthChangeEvent(Long playerUid) {
        this.playerUid = playerUid;
    }

    public Long getPlayerUid() {
        return playerUid;
    }

    private final Long playerUid;
}
