package com.wan37.config.excel;

import com.wan37.config.ConfigFactory;
import com.wan37.logic.test.config.TestCfg;
import com.wan37.logic.test.config.impl.TestCfgImpl;

/**
 * 测试配置表实体类
 *
 * @author linda
 */
public class TestCfgExcel implements ConfigFactory<TestCfg> {

    private Integer id;
    private String name;
    private double value;
    private boolean sex;

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Override
    public TestCfg create() {
        return new TestCfgImpl();
    }
}
