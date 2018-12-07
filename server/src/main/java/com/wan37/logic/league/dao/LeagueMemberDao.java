package com.wan37.logic.league.dao;

import com.wan37.logic.league.database.LeagueMemberDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public interface LeagueMemberDao extends Repository<LeagueMemberDb, Long> {

    void save(LeagueMemberDb memberDb);
}
