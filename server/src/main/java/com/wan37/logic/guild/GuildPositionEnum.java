package com.wan37.logic.guild;

import java.util.Objects;

/**
 * 公会职位枚举
 *
 * @author linda
 */
public enum GuildPositionEnum {

    /**
     * 会长
     */
    CHAIRMAN(1, "会长"),

    /**
     * 副会长
     */
    VICE_CHAIRMAN(2, "副会长"),

    /**
     * 精英
     */
    ELITE(3, "精英"),

    /**
     * 普通成员
     */
    ORDINARY(4, "普通成员");

    private Integer id;
    private String name;

    GuildPositionEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static String getName(Integer id) {
        for (GuildPositionEnum position : GuildPositionEnum.values()) {
            if (Objects.equals(position.id, id)) {
                return position.name;
            }
        }
        return "NULL";
    }
}
