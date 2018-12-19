package com.wan37.logic.team.entity;

import java.util.List;

public interface ITeam {

    interface Factory {

        ITeam create(Long leaderUid);
    }

    void lock();

    void unLock();

    Long getUid();

    Long getLeaderUid();

    void setLeaderUid(Long playerUid);

    void addMember(ITeamMember member);

    void rmMember(Long playerUid);

    List<ITeamMember> getMembers();

    ITeamMember getMember(Long playerUid);
}
