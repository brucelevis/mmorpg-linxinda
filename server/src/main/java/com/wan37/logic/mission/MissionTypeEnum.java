package com.wan37.logic.mission;

public enum MissionTypeEnum {

    MISSION_TYPE_1(1, "击杀怪物"),
    MISSION_TYPE_2(2, "提升等级"),
    MISSION_TYPE_3(3, "npc对话"),
    MISSION_TYPE_4(4, "某种虚物达到多少"),
    MISSION_TYPE_5(5, "添加一个好友"),
    MISSION_TYPE_6(6, "装备穿戴等级总和达到多少"),
    MISSION_TYPE_7(7, "第一次加入组队"),
    MISSION_TYPE_8(8, "第一次加入公会"),
    MISSION_TYPE_9(9, "第一次与玩家交易成功"),
    MISSION_TYPE_10(10, "完成系列任务"),
    MISSION_TYPE_11(11, "通关某个副本n次"),
    MISSION_TYPE_12(12, "获得n件达到最高品级的装备");

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
