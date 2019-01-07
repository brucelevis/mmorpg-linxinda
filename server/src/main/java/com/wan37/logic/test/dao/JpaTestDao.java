package com.wan37.logic.test.dao;

import com.wan37.logic.test.database.JpaTestDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author linda
 */
@Service
public interface JpaTestDao extends Repository<JpaTestDb, Integer> {

    JpaTestDb getById(Integer id);

    void save(JpaTestDb db);

    @Transactional
    void deleteById(Integer id);
}
