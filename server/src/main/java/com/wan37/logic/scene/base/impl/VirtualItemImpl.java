package com.wan37.logic.scene.base.impl;

import com.wan37.logic.props.config.VirtualItemCfg;
import com.wan37.logic.scene.base.SceneItem;

class VirtualItemImpl implements SceneItem {

    public VirtualItemImpl(Long uid, VirtualItemCfg virtualItemCfg, int amount, long expireTime) {
        this.uid = uid;
        this.virtualItemCfg = virtualItemCfg;
        this.amount = amount;
        this.expireTime = expireTime;
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public Integer getCfgId() {
        return virtualItemCfg.getId();
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getName() {
        return virtualItemCfg.getName();
    }

    @Override
    public long getExpireTime() {
        return expireTime;
    }

    private final Long uid;
    private final VirtualItemCfg virtualItemCfg;
    private final int amount;
    private final long expireTime;
}
