package com.wan37.config.entity;

/**
 * 门派职业配置表实体类
 *
 * @author linda
 */
public class FactionCfgExcel {

    private Integer id;
    private String name;
    private String initAttr;
    private String initSkill;

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

    public String getInitSkill() {
        return initSkill;
    }

    public void setInitSkill(String initSkill) {
        this.initSkill = initSkill;
    }
}
