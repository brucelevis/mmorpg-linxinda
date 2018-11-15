package com.wan37.logic.buff;

public enum BuffType1Enum {

    BUFF_TYPE_1(1, "攻击Buff"),
    BUFF_TYPE_2(2, "防御Buff"),
    BUFF_TYPE_3(3, "持续Buff"),
    BUFF_TYPE_4(4, "异常Buff");

    private Integer id;
    private String name;

    BuffType1Enum(Integer id, String name) {
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
