package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.LeaguePositionEnum;
import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.util.NetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class ILeagueImpl implements ILeague {

    public ILeagueImpl(Map<Long, ILeagueMember> memberMap, LeagueGlobalDb leagueGlobalDb, ILWarehouse warehouse, LeagueDao leagueDao, NetUtil netUtil) {
        this.memberMap = memberMap;
        this.leagueGlobalDb = leagueGlobalDb;
        this.warehouse = warehouse;
        this.leagueDao = leagueDao;
        this.netUtil = netUtil;
    }

    @Override
    public Long getUid() {
        return leagueGlobalDb.getUid();
    }

    @Override
    public String getName() {
        return leagueGlobalDb.getName();
    }

    @Override
    public Long getCreateTime() {
        return leagueGlobalDb.getCreateTime();
    }

    @Override
    public int getMaxNum() {
        return leagueGlobalDb.getMaxNum();
    }

    @Override
    public int getCurNum() {
        return leagueGlobalDb.getMembers().size();
    }

    @Override
    public List<ILeagueMember> getMembers() {
        return new ArrayList<>(memberMap.values());
    }

    @Override
    public ILeagueMember getMember(Long playerUid) {
        return memberMap.get(playerUid);
    }

    @Override
    public void addMember(ILeagueMember member) {
        if (memberMap.containsKey(member.getPlayerUid())) {
            return;
        }
        memberMap.put(member.getPlayerUid(), member);
        leagueGlobalDb.getMembers().add(member.getLeagueMemberDb());
    }

    @Override
    public void rmMember(Long playerUid) {
        memberMap.remove(playerUid);

        leagueGlobalDb.setMembers(leagueGlobalDb.getMembers().stream()
                .filter(m -> !Objects.equals(m.getPlayerUid(), playerUid))
                .collect(Collectors.toSet()));
    }

    @Override
    public void notifyAll(String msg) {
        netUtil.send(msg, leagueGlobalDb.getMembers().stream()
                .map(LeagueMemberDb::getPlayerUid)
                .collect(Collectors.toSet()));
    }

    @Override
    public void save() {
        leagueDao.save(leagueGlobalDb);
    }

    @Override
    public Long getLeaderUid() {
        return memberMap.values().stream()
                .filter(m -> Objects.equals(m.getPosition(), LeaguePositionEnum.LEAGUE_POSITION_1.getId()))
                .map(ILeagueMember::getPlayerUid)
                .findAny()
                .orElseThrow(() -> new RuntimeException("公会找不到会长"));
    }

    @Override
    public ILWarehouse getWarehouse() {
        return warehouse;
    }

    private final Map<Long, ILeagueMember> memberMap;
    private final LeagueGlobalDb leagueGlobalDb;
    private final ILWarehouse warehouse;

    private final LeagueDao leagueDao;
    private final NetUtil netUtil;
}
