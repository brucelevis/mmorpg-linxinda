package com.wan37.common;

public enum ResultCode {

    SUCCESS(1, "成功"),
    ERROR(0, "失败"),

    LOGIN_SUCCESS(10, "登录成功"),
    ROLE_NOT_EXIST(11, "找不到角色");

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
