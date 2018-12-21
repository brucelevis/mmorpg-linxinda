package com.wan37.logic.mission;

public enum MissionTypeEnum {

    MISSION_TYPE_1(1, "击杀怪物"),
    MISSION_TYPE_2(2, "提升等级");

    private Integer id;
    private String name;

    MissionTypeEnum(Integer id, String name) {
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
