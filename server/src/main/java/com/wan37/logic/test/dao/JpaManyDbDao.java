package com.wan37.logic.test.dao;

import com.wan37.logic.test.database.JpaManyDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试一对多 多方
 *
 * @author linda
 */
@Service
public interface JpaManyDbDao extends Repository<JpaManyDb, Long> {

    void save(JpaManyDb db);

    @Transactional
    void deleteAll();

    @Transactional
    void deleteByAny(String any);
}
