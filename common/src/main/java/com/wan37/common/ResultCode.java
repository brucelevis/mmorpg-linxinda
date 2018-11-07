package com.wan37.common;

public enum ResultCode {

    SUCCESS(1, "成功"),
    ERROR(0, "失败"),

    // 登录注册
    LOGIN_SUCCESS(10, "登录成功"),
    ROLE_NOT_EXIST(11, "找不到角色"),
    REGISTER_SUCCESS(12, "注册成功"),

    // 场景
    SCENE_PLAYER_ENTER(20, "玩家进入场景推送"),
    SCENE_PLAYER_LEAVE(21, "玩家离开场景推送"),
    SCENE_AOI(22, "场景内所有实物"),

    // 背包系统
    BACKPACK_INFO(30, "玩家背包物品信息"),
    BACKPACK_UPDATE(31, "背包格子物品信息更新"),

    // 虚拟物品系统（货币，积分）
    CURRENCY_UPDATE(40, "虚拟物品信息更新"),

    // 属性系统
    ATTR_INFO(50, "玩家属性信息");

    private Long code;
    private String desc;

    ResultCode(long code, String desc) {
        this.setCode(code);
        this.setDesc(desc);
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
