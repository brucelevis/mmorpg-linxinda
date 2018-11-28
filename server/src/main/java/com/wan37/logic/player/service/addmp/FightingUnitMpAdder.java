package com.wan37.logic.player.service.addmp;

import com.wan37.logic.attack.fighting.FightingUnit;
import org.springframework.stereotype.Service;

@Service
public class FightingUnitMpAdder {

    public void add(FightingUnit unit, int addMp) {
        long cur = unit.getMp();
        long maxMp = unit.getMaxMp();

        long result = maxMp > cur + addMp ? cur + addMp : maxMp;
        if (result == cur) {
            // 没变化
            return;
        }

        unit.setMp(result);
    }
}
