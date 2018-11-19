package com.wan37.logic.buff;

import com.wan37.logic.buff.config.BuffCfg;

public interface IBuff {

    interface Factory {

        IBuff create(BuffCfg buffCfg);
    }

    Integer getId();

    Integer getEffectId();

    boolean isOnce();

    String getName();

    long getExpireTime();

    long getLastEffectTime();

    void setLastEffectTime(long time);

    int getInterval();

    String getArg();
}
