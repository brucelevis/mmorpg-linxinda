package com.wan37.logic.league;

import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.database.LeagueRootDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LeagueGlobalManager {

    private static Map<Long, LeagueRootDb> leagueMap = new ConcurrentHashMap<>();

    @Autowired
    private LeagueDao leagueDao;

    public void addLeague(LeagueRootDb rootDb) {
        leagueMap.put(rootDb.getUid(), rootDb);
    }

    public LeagueRootDb getLeague(Long uid) {
        LeagueRootDb rootDb = leagueMap.get(uid);
        if (rootDb == null) {
            return leagueDao.findByUid(uid);
        }

        return rootDb;
    }
}
