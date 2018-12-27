package com.wan37.logic.player.service;

import com.wan37.logic.scene.base.FightingUnit;
import org.springframework.stereotype.Service;

@Service
public class FightingUnitHpAdder {

    public void add(FightingUnit unit, long addHp) {
        long cur = unit.getHp();
        long maxHp = unit.getMaxHp();

        long result = maxHp > cur + addHp ? cur + addHp : maxHp;
        if (result == cur) {
            // 没变化
            return;
        }

        unit.setHp(result);
    }
}
