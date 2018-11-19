package com.wan37.logic.buff;

import com.wan37.logic.buff.config.BuffCfg;
import com.wan37.util.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class IBuffFactory implements IBuff.Factory {

    @Override
    public IBuff create(BuffCfg buffCfg) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        long expireTime = now + TimeUnit.MILLISECONDS.toMillis(buffCfg.getContinuous());

        return new IBuffImpl(expireTime, buffCfg, 0);
    }
}
