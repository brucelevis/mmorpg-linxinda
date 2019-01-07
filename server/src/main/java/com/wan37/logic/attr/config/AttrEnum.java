package com.wan37.logic.attr.config;

/**
 * 特殊属性值（血，蓝）
 *
 * @author linda
 */
public enum AttrEnum {

    /**
     * 血量
     */
    ATTR_HP(1, "血量"),

    /**
     * 蓝量
     */
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
