package com.wan37.event.entity;

import com.wan37.logic.scene.base.FightingUnit;

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
