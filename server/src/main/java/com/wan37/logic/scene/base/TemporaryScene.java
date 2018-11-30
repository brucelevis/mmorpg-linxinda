package com.wan37.logic.scene.base;

public abstract class TemporaryScene extends AbstractScene {

    private long expireTime;

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
