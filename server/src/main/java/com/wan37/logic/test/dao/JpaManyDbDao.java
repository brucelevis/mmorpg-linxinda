package com.wan37.logic.test.dao;

import com.wan37.logic.test.database.JpaManyDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface JpaManyDbDao extends Repository<JpaManyDb, Long> {

    JpaManyDb getById(Long id);

    void save(JpaManyDb db);

    @Transactional
    void deleteById(Long id);

    @Transactional
    void deleteAll();

    @Transactional
    void deleteByAny(String any);
}
