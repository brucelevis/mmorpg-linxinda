package com.wan37.logic.buff;

public enum BuffTypeEnum {

    BUFF_TYPE_1(1, "增益类"),
    BUFF_TYPE_2(2, "减益类"),
    BUFF_TYPE_3(3, "特殊类");

    private Integer id;
    private String name;

    BuffTypeEnum(Integer id, String name) {
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
