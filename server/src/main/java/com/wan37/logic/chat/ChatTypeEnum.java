package com.wan37.logic.chat;

/**
 * 聊天类型
 *
 * @author linda
 */
public enum ChatTypeEnum {

    /**
     * 当前
     */
    SCENE(1, "当前"),

    /**
     * 悄悄话
     */
    PRIVATE(2, "悄悄话"),

    /**
     * 世界
     */
    WORLD(3, "世界");

    private Integer id;
    private String name;

    ChatTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
