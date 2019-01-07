package com.wan37.config.entity;

/**
 * 召唤兽配置表实体类
 *
 * @author linda
 */
public class SummoningCfgExcel {

    private Integer id;
    private String name;
    private String initAttr;
    private String skills;

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

    public String getInitAttr() {
        return initAttr;
    }

    public void setInitAttr(String initAttr) {
        this.initAttr = initAttr;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
