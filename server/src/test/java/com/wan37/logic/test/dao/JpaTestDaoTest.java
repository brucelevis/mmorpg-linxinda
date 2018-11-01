package com.wan37.logic.test.dao;

import base.BaseTest;
import com.wan37.logic.test.database.JpaTestDb;
import com.wan37.logic.test.database.JpaTestSubDb;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaTestDaoTest extends BaseTest {

    @Autowired
    JpaTestDao _sut;

    @Test
    public void getById() {
        // Arrange

        // Act
        JpaTestDb result = _sut.getById(101);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void save() {
        // Arrange
        JpaTestDb db = new JpaTestDb();
        db.setId(101);
        db.setName("test save");

        JpaTestSubDb subDb = new JpaTestSubDb();
        subDb.setId(999);
        db.setJpaTestSubDb(subDb);

        db.getUids().add(11111L);

        // Act
        _sut.save(db);
        JpaTestDb result = _sut.getById(101);

        // Assert
        assertThat(result.getId()).isEqualTo(101);
        assertThat(result.getJpaTestSubDb().getId()).isEqualTo(999);
        assertThat(result.getUids()).contains(11111L);
    }

    @Test
    public void deleteById() {
        // Arrange
        JpaTestDb db = new JpaTestDb();
        db.setId(101);

        // Act
        _sut.save(db);
        _sut.deleteById(101);
        JpaTestDb result = _sut.getById(101);

        // Assert
        assertThat(result).isNull();
    }
}