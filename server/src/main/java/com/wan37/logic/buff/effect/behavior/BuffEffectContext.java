package com.wan37.logic.buff.effect.behavior;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.Buff;

/**
 * @author linda
 */
public class BuffEffectContext {

    public BuffEffectContext(FightingUnit unit, Buff buff, long now) {
        this.unit = unit;
        this.buff = buff;
        this.now = now;
    }

    public FightingUnit getUnit() {
        return unit;
    }

    public Buff getBuff() {
        return buff;
    }

    public long getNow() {
        return now;
    }

    private final FightingUnit unit;
    private final Buff buff;
    private final long now;
}
