package com.wan37.logic.team.entity;

public interface ITeamMember {

    interface Factory {

        ITeamMember create(Long playerUid);
    }

    Long getPlayerUid();

    boolean isOnline();
}
