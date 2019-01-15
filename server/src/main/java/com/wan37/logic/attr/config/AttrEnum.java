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
    HP(1),

    /**
     * 蓝量
     */
    MP(2);

    private Integer id;

    AttrEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
