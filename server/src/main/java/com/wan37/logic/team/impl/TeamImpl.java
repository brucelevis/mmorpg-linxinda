package com.wan37.logic.team.impl;

import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.Team;
import com.wan37.logic.team.TeamMember;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

class TeamImpl implements Team {

    public TeamImpl(Long uid, Lock lock, Map<Long, TeamMember> members, PlayerGlobalManager playerGlobalManager, Long leaderUid) {
        this.uid = uid;
        this.lock = lock;
        this.members = members;
        this.playerGlobalManager = playerGlobalManager;
        this.leaderUid = leaderUid;
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unLock() {
        lock.unlock();
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public Long getLeaderUid() {
        return leaderUid;
    }

    @Override
    public void addMember(TeamMember member) {
        members.put(member.getPlayerUid(), member);
    }

    @Override
    public void rmMember(Long playerUid) {
        members.remove(playerUid);
    }

    @Override
    public List<TeamMember> getMembers() {
        return new ArrayList<>(members.values());
    }

    @Override
    public TeamMember getMember(Long playerUid) {
        return members.get(playerUid);
    }

    @Override
    public void broadcast(String msg) {
        members.values().stream()
                .filter(TeamMember::isOnline)
                .map(m -> playerGlobalManager.getPlayerByUid(m.getPlayerUid()))
                .forEach(p -> p.syncClient(msg));
    }

    private final Long uid;
    private final Lock lock;
    private final Map<Long, TeamMember> members;
    private final PlayerGlobalManager playerGlobalManager;

    private Long leaderUid;
}
