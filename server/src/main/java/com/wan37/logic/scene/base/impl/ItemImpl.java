package com.wan37.logic.scene.base.impl;

import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.scene.base.SceneItem;

class ItemImpl implements SceneItem {

    public ItemImpl(Long uid, PropsCfg propsCfg, int amount, long expireTime) {
        this.uid = uid;
        this.propsCfg = propsCfg;
        this.amount = amount;
        this.expireTime = expireTime;
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public Integer getCfgId() {
        return propsCfg.getId();
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getName() {
        return propsCfg.getName();
    }

    @Override
    public long getExpireTime() {
        return expireTime;
    }

    private final Long uid;
    private final PropsCfg propsCfg;
    private final int amount;
    private final long expireTime;
}
