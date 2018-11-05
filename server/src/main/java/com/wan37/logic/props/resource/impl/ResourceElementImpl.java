package com.wan37.logic.props.resource.impl;

import com.wan37.logic.props.resource.ResourceElement;

public class ResourceElementImpl implements ResourceElement {

    public ResourceElementImpl(Integer cfgId, long amount) {
        this.cfgId = cfgId;
        this.amount = amount;
    }

    @Override
    public Integer getCfgId() {
        return cfgId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    private final Integer cfgId;
    private final long amount;
}
