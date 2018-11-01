package com.wan37.common.resp;

public class RespAoiSceneMonsterDto {

    private Long uid;

    private Integer cfgId;

    private String name;

    public Long getUid() {
        return uid;
    }

    public Integer getCfgId() {
        return cfgId;
    }

    public void setCfgId(Integer cfgId) {
        this.cfgId = cfgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
