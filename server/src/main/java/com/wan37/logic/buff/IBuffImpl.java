package com.wan37.logic.buff;

import com.wan37.logic.buff.config.BuffCfg;

class IBuffImpl implements IBuff {

    public IBuffImpl(long expireTime, BuffCfg buffCfg, long lastEffectTime) {
        this.expireTime = expireTime;
        this.buffCfg = buffCfg;
        this.lastEffectTime = lastEffectTime;
    }

    @Override
    public Integer getId() {
        return buffCfg.getId();
    }

    @Override
    public Integer getEffectId() {
        return buffCfg.getEffectId();
    }

    @Override
    public boolean isOnce() {
        return buffCfg.isOnce();
    }

    @Override
    public String getName() {
        return buffCfg.getName();
    }

    @Override
    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public long getLastEffectTime() {
        return lastEffectTime;
    }

    @Override
    public void setLastEffectTime(long time) {
        lastEffectTime = time;
    }

    @Override
    public int getInterval() {
        return buffCfg.getInterval();
    }

    @Override
    public String getArg() {
        return buffCfg.getArg();
    }

    @Override
    public int getContinuous() {
        return buffCfg.getContinuous();
    }

    @Override
    public Integer getTarget() {
        return buffCfg.getTatget();
    }

    private final long expireTime;
    private final BuffCfg buffCfg;

    private long lastEffectTime;
}
