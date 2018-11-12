package com.wan37.logic.equipment;

public enum EquipPartEnum {

    PART_1(1, "武器"),
    PART_2(2, "手套"),
    PART_3(3, "帽子");

    private Integer id;
    private String name;

    EquipPartEnum(Integer id, String name) {
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

    public static String getName(int id) {
        for (EquipPartEnum part : EquipPartEnum.values()) {
            if (part.getId() == id) {
                return part.name;
            }
        }
        return null;
    }
}
