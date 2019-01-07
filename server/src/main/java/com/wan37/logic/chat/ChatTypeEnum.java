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
    CHAT_TYPE_SCENE(1, "当前"),

    /**
     * 悄悄话
     */
    CHAT_TYPE_PRIVATE(2, "悄悄话"),

    /**
     * 世界
     */
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
