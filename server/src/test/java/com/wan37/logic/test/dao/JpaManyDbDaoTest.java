package com.wan37.logic.test.dao;

import base.BaseTest;
import com.google.common.collect.ImmutableList;
import com.wan37.logic.test.database.JpaManyDb;
import com.wan37.logic.test.database.JpaOneDb;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaManyDbDaoTest extends BaseTest {

    @Autowired
    JpaManyDbDao manyDbDao;

    @Autowired
    JpaOneDbDao oneDbDao;

    @Test
    public void save_one() {
        // Arrange
        Long id = 1L;

        JpaOneDb one = new JpaOneDb();
        one.setId(id);

        JpaManyDb many = new JpaManyDb();
        many.setAny("who care");
        many.setOne(one);

        one.setMany(ImmutableList.of(many));

        // Act
        oneDbDao.save(one);
        JpaOneDb result = oneDbDao.getById(id);
        oneDbDao.deleteById(id);

        // Assert
        assertThat(result.getMany().get(0).getAny()).isEqualTo("who care");
    }

    @Test
    public void saveAndDelete_many() {
        // Arrange
        Long id = 1L;

        JpaOneDb one = new JpaOneDb();
        one.setId(id);

        JpaManyDb many1 = new JpaManyDb();
        many1.setAny("who care1");
        many1.setOne(one);

        JpaManyDb many2 = new JpaManyDb();
        many2.setAny("who care2");
        many2.setOne(one);

        // Act
        oneDbDao.save(one);
        manyDbDao.save(many1);
        manyDbDao.save(many2);

        JpaOneDb result1 = oneDbDao.getById(id);

        manyDbDao.deleteByAny(many1.getAny());
        JpaOneDb result2 = oneDbDao.getById(id);

        manyDbDao.deleteAll();
        oneDbDao.deleteAll();

        // Assert
        assertThat(result1.getMany().size()).isEqualTo(2);

        assertThat(result2.getMany().get(0).getAny()).isEqualTo("who care2");
    }
}