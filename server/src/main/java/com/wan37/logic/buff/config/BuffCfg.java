package com.wan37.logic.buff.config;

public interface BuffCfg {

    Integer getId();

    String getName();

    Integer getEffectId();

    String getDesc();

    boolean isOnce();

    String getArg();

    int getInterval();

    int getContinuous();

    Integer getTatget();
}
