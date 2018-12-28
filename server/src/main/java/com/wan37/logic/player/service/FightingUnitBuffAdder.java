package com.wan37.logic.player.service;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.entity.IBuff;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FightingUnitBuffAdder {

    public void add(FightingUnit unit, IBuff buff) {
        // 去重
        List<IBuff> buffs = unit.getBuffs();
        buffs.stream().filter(b -> com.google.common.base.Objects.equal(b.getId(), buff.getId()))
                .findAny()
                .ifPresent(buffs::remove);

        buffs.add(buff);
    }
}
