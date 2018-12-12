package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.entity.ILWarehouse;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.util.NetTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class ILeagueFactoryImpl implements ILeague.Factory {

    @Autowired
    private LeagueDao leagueDao;

    @Autowired
    private NetTool netTool;

    @Autowired
    private ILeagueMember.Factory leagueMemberFactory;

    @Autowired
    private ILWarehouse.Factory warehouseFactory;

    @Override
    public ILeague create(LeagueGlobalDb leagueGlobalDb) {
        if (leagueGlobalDb == null) {
            return null;
        }

        Map<Long, ILeagueMember> memberMap = leagueGlobalDb.getMembers().stream()
                .map(m -> leagueMemberFactory.create(m))
                .collect(Collectors.toMap(ILeagueMember::getPlayerUid, Function.identity()));

        ILWarehouse warehouse = warehouseFactory.create(leagueGlobalDb);
        return new ILeagueImpl(memberMap, leagueGlobalDb, warehouse, leagueDao, netTool);
    }
}
