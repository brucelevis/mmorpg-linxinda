package com.wan37.logic.test.dao;

import com.wan37.logic.test.database.JpaTestDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public interface JpaTestDao extends Repository<JpaTestDb, Integer> {

    JpaTestDb getById(Integer id);

    void
}
