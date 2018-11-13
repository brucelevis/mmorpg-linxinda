package com.wan37.config.entity;

public class MonsterCfgExcel {

    private Integer id;
    private String name;
    private String initAttr;
    private int createCd;
    private int exp;

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

    public int getCreateCd() {
        return createCd;
    }

    public void setCreateCd(int createCd) {
        this.createCd = createCd;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
