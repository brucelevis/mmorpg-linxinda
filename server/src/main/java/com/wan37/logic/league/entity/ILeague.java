package com.wan37.logic.league.entity;

import com.wan37.logic.league.database.LeagueGlobalDb;

import java.util.List;

public interface ILeague {

    interface Factory {

        ILeague create(LeagueGlobalDb leagueGlobalDb);
    }

    Long getUid();

    String getName();

    Long getCreateTime();

    int getMaxNum();

    int getCurNum();

    List<ILeagueMember> getMembers();

    ILeagueMember getMember(Long playerUid);

    void addMember(ILeagueMember member);

    void rmMember(Long playerUid);

    void notifyAll(String msg);

    void save();

    Long getLeaderUid();
}
