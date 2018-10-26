package com.wan37.logic.test.database;

import javax.persistence.*;

/**
 * 测试Jpa的表
 */
@Entity
@Table(name = "jpa_test")
public class JpaTestDb {

    @Id
    private Integer id;

    private String name;

    private int d1;

    private int d2;

    @Convert(converter = JpaTestSubDbAttrConverterImpl.class)
    private JpaTestSubDb jpaTestSubDb;
}
