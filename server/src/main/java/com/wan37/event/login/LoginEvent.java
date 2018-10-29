package com.wan37.event.login;

public class LoginEvent {

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    private Long playerId;
}
