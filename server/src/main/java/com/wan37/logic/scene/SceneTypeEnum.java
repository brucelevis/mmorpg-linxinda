package com.wan37.logic.scene;

public enum SceneTypeEnum {

    SCENE_TYPE_1(1, "普通场景"),
    SCENE_TYPE_2(2, "副本场景"),
    SCENE_TYPE_3(3, "竞技场场景");

    private Integer id;
    private String name;

    SceneTypeEnum(Integer id, String name) {
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
