package com.wan37.logic.test.database;

import javax.persistence.*;
import java.util.List;

/**
 * @author linda
 */
@Entity
@Table(name = "jpa_test_one")
public class JpaOneDb {

    @Id
    private Long id;

    /**
     * 级联保存、更新、删除、刷新;立即加载。当删除用户，会级联删除关联所有的JpaManyDb
     * 拥有mappedBy注解的实体类为关系被维护端
     * mappedBy="one"中的one是JpaManyDb中的one属性
     */
    @OneToMany(mappedBy = "one", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<JpaManyDb> many;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JpaManyDb> getMany() {
        return many;
    }

    public void setMany(List<JpaManyDb> many) {
        this.many = many;
    }
}
