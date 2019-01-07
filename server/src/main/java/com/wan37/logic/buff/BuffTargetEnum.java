package com.wan37.logic.buff;

/**
 * Buff目标
 *
 * @author linda
 */
public enum BuffTargetEnum {

    /**
     * 自己
     */
    BUFF_TARGET_1(1, "自己"),

    /**
     * 敌人
     */
    BUFF_TARGET_2(2, "敌人");

    private Integer id;
    private String name;

    BuffTargetEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
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
