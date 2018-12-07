package com.wan37.logic.league.dao;

import com.wan37.logic.league.database.LeagueRootDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public interface LeagueDao extends Repository<LeagueRootDb, Long> {

    LeagueRootDb save(LeagueRootDb leagueRootDb);

    boolean existsByName(String name);

    LeagueRootDb findByUid(Long uid);
}
