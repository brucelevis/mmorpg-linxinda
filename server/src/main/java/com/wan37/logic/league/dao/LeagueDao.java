package com.wan37.logic.league.dao;

import com.wan37.logic.league.database.LeagueGlobalDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public interface LeagueDao extends Repository<LeagueGlobalDb, Long> {

    LeagueGlobalDb save(LeagueGlobalDb leagueGlobalDb);

    boolean existsByName(String name);

    LeagueGlobalDb findByUid(Long uid);
}
