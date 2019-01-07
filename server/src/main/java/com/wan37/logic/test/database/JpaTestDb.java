package com.wan37.logic.test.database;

import com.wan37.logic.test.database.convert.JpaTestSubDbAttrConverterImpl;
import com.wan37.logic.test.database.convert.UidSetConverterImpl;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * 测试Jpa的表
 *
 * @author linda
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

    @Convert(converter = UidSetConverterImpl.class)
    private Set<Long> uids = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getD1() {
        return d1;
    }

    public void setD1(int d1) {
        this.d1 = d1;
    }

    public int getD2() {
        return d2;
    }

    public void setD2(int d2) {
        this.d2 = d2;
    }

    public JpaTestSubDb getJpaTestSubDb() {
        return jpaTestSubDb;
    }

    public void setJpaTestSubDb(JpaTestSubDb jpaTestSubDb) {
        this.jpaTestSubDb = jpaTestSubDb;
    }

    public Set<Long> getUids() {
        return uids;
    }

    public void setUids(Set<Long> uids) {
        this.uids = uids;
    }
}
