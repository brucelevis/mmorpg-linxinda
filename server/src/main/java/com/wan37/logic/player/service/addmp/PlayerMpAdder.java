package com.wan37.logic.player.service.addmp;

import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.attr.config.AttrEnum;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayerMpAdder {

    public void add(FightingUnit unit, int addMp) {
        long cur = unit.getMp();
        long mp = cur + addMp;

        Map<Integer, Double> attrs = unit.getAttrs();
        Integer mpId = AttrEnum.ATTR_MP.getId();
        if (!attrs.containsKey(mpId)) {
            return;
        }

        long max = attrs.get(mpId).longValue();
        long result = max > mp ? mp : max;
        if (result == cur) {
            // 没变化
            return;
        }

        unit.setMp(result);
    }
}
