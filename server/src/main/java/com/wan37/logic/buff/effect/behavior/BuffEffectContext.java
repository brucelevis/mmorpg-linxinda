package com.wan37.logic.buff.effect.behavior;

import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.buff.IBuff;

public class BuffEffectContext {

    public BuffEffectContext(FightingUnit unit, IBuff buff, long now) {
        this.unit = unit;
        this.buff = buff;
        this.now = now;
    }

    public FightingUnit getUnit() {
        return unit;
    }

    public IBuff getBuff() {
        return buff;
    }

    public long getNow() {
        return now;
    }

    private final FightingUnit unit;
    private final IBuff buff;
    private final long now;
}
