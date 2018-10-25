package com.wan37.common;

public enum ResultCode {

    SUCESS(1, "成功"),
    ERROR(0, "失败");

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

    public static String getDesc(long code) {
        for (ResultCode c : ResultCode.values()) {
            if (c.getCode() == code) {
                return c.getDesc();
            }
        }
        return null;
    }
}
