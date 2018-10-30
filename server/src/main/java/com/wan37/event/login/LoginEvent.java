package com.wan37.event.login;

public class LoginEvent {

    public LoginEvent(Long playerUid) {
        this.playerUid = playerUid;
    }

    public Long getPlayerUid() {
        return playerUid;
    }

    private final Long playerUid;
}
