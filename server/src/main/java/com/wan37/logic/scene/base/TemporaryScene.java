package com.wan37.logic.scene.base;

public abstract class TemporaryScene extends AbstractScene {

    private Long uid;

    private long expireTime;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
