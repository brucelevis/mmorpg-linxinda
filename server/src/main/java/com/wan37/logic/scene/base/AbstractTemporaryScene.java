package com.wan37.logic.scene.base;

/**
 * 临时场景抽象父类
 *
 * @author linda
 */
public abstract class AbstractTemporaryScene extends AbstractScene {

    private long expireTime;

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
