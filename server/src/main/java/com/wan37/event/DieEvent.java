package com.wan37.event;

import com.wan37.logic.attack.fighting.FightingUnit;

public class DieEvent {

    public DieEvent(FightingUnit unit, long now) {
        this.unit = unit;
        this.now = now;
    }

    public FightingUnit getUnit() {
        return unit;
    }

    public long getNow() {
        return now;
    }

    private final FightingUnit unit;
    private final long now;
}
