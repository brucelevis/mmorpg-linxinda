package com.wan37.logic.chat;

public enum ChatTypeEnum {

    CHAT_TYPE_SCENE(1, "当前"),
    CHAT_TYPE_PRIVATE(2, "悄悄话"),
    CHAT_TYPE_WORLD(3, "世界");

    private Integer id;
    private String name;

    ChatTypeEnum(Integer id, String name) {
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
