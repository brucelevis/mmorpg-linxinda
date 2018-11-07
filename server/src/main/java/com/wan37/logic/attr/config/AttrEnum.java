package com.wan37.logic.attr.config;

public enum AttrEnum {

    ATTR_HP(1, "血量"),
    ATTR_MP(2, "蓝量");

    private Integer id;
    private String name;

    AttrEnum(Integer id, String name) {
        this.setId(id);
        this.setName(name);
    }

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
}
