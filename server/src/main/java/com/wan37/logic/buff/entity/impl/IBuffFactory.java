package com.wan37.logic.buff.entity.impl;

import com.wan37.logic.buff.config.BuffCfg;
import com.wan37.logic.buff.entity.Buff;
import com.wan37.util.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author linda
 */
@Service
public class IBuffFactory implements Buff.Factory {

    @Override
    public Buff create(BuffCfg buffCfg) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        long expireTime = now + TimeUnit.MILLISECONDS.toMillis(buffCfg.getContinuous());

        return new BuffImpl(expireTime, buffCfg, 0);
    }
}
