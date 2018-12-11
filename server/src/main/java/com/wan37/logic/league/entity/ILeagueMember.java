package com.wan37.logic.league.entity;

import com.wan37.logic.league.database.LeagueMemberDb;

public interface ILeagueMember {

    interface Factory {

        ILeagueMember create(LeagueMemberDb leagueMemberDb);
    }

    LeagueMemberDb getLeagueMemberDb();

    Long getPlayerUid();

    Integer getPosition();

    void setPosition(Integer position);

    String getName();
}
