package com.wan37.logic.test.dao;

import com.wan37.logic.test.database.JpaOneDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface JpaOneDbDao extends Repository<JpaOneDb, Long> {

    JpaOneDb getById(Long id);

    void save(JpaOneDb db);

    @Transactional
    void deleteById(Long id);

    @Transactional
    void deleteAll();
}
