package com.wan37.logic.league.dao;

import com.wan37.logic.league.database.LeagueGlobalDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface LeagueDao extends Repository<LeagueGlobalDb, Long> {

    LeagueGlobalDb save(LeagueGlobalDb leagueGlobalDb);

    boolean existsByName(String name);

    LeagueGlobalDb findByUid(Long uid);

    @Transactional
    void removeByUid(Long uid);
}
