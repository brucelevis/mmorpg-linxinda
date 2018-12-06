package com.wan37.logic.player.dao;

import com.wan37.logic.player.database.PlayerDb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public interface PlayerDao extends Repository<PlayerDb, Long> {

    PlayerDb getByUid(Long uid);

    void save(PlayerDb playerDb);

    boolean existsByUid(Long uid);

    @Query(value = "select p.name from PlayerDb p where p.uid = ?1")
    String find(Long uid);
}
