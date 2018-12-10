package com.wan37.logic.league;

import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.database.LeagueGlobalDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LeagueGlobalManager {

    private static Map<Long, LeagueGlobalDb> leagueMap = new ConcurrentHashMap<>();

    @Autowired
    private LeagueDao leagueDao;

    public void addLeague(LeagueGlobalDb rootDb) {
        leagueMap.put(rootDb.getUid(), rootDb);
    }

    public LeagueGlobalDb getLeague(Long uid) {
        LeagueGlobalDb rootDb = leagueMap.get(uid);
        if (rootDb == null) {
            return leagueDao.findByUid(uid);
        }

        return rootDb;
    }
}
