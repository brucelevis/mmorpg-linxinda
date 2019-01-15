package com.wan37.logic.equipment;

import java.util.Objects;

/**
 * 装备部位枚举类
 *
 * @author linda
 */
public enum EquipPartEnum {

    /**
     * 武器
     */
    WEAPON(1, "武器"),

    /**
     * 手套
     */
    GLOVE(2, "手套"),

    /**
     * 帽子
     */
    CAP(3, "帽子");

    private Integer id;
    private String name;

    EquipPartEnum(Integer id, String name) {
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
        for (EquipPartEnum part : EquipPartEnum.values()) {
            if (Objects.equals(part.id, id)) {
                return part.name;
            }
        }
        return "NULL";
    }
}
