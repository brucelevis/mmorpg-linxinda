package com.wan37.logic.test.database;

import javax.persistence.*;

/**
 * @author linda
 */
@Entity
@Table(name = "jpa_test_many")
public class JpaManyDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String any;

    /**
     * 可选属性optional=false,表示JpaOneDb不能为空。删除JpaManyDb，不影响JpaOneDb
     * 设置在JpaManyDb表中的关联字段(外键)
     * <p>
     * ###注意：
     * cascade = CascadeType.ALL 只能写在 One 端，只有One端改变Many端，不准Many端改变One端。
     * 特别是删除，因为 ALL 里包括更新，删除。
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "one_id")
    private JpaOneDb one;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAny() {
        return any;
    }

    public void setAny(String any) {
        this.any = any;
    }

    public JpaOneDb getOne() {
        return one;
    }

    public void setOne(JpaOneDb one) {
        this.one = one;
    }
}
