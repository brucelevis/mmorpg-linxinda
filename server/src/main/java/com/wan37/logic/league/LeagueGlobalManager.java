package com.wan37.logic.league;

import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.database.LeagueGlobalDb;
import com.wan37.logic.league.entity.ILeague;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LeagueGlobalManager {

    /**
     * key：leagueUid
     */
    private static Map<Long, ILeague> leagueMap = new ConcurrentHashMap<>();

    @Autowired
    private LeagueDao leagueDao;

    @Autowired
    private ILeague.Factory leagueFactory;

    public void addLeague(ILeague league) {
        if (league == null) {
            return;
        }

        leagueMap.put(league.getUid(), league);
    }

    public ILeague get(Long uid) {
        if (!leagueMap.containsKey(uid)) {
            LeagueGlobalDb leagueGlobalDb = leagueDao.findByUid(uid);
            ILeague league = leagueFactory.create(leagueGlobalDb);
            addLeague(league);
        }

        return leagueMap.get(uid);
    }
}
