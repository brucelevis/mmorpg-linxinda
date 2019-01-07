package com.wan37.event.entity;

/**
 * 战力变化事件
 *
 * @author linda
 */
public class StrengthChangeEvent {

    public StrengthChangeEvent(Long playerUid) {
        this.playerUid = playerUid;
    }

    public Long getPlayerUid() {
        return playerUid;
    }

    private final Long playerUid;
}
