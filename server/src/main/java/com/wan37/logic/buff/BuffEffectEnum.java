package com.wan37.logic.buff;

public enum BuffEffectEnum {

    BUFF_EFFECT_1(1, "持续回蓝"),
    BUFF_EFFECT_2(2, "持续扣血"),
    BUFF_EFFECT_3(3, "护盾");

    private Integer id;
    private String name;

    BuffEffectEnum(Integer id, String name) {
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
