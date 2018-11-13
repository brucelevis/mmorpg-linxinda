package com.wan37.logic.monster.init;

import com.wan37.logic.attr.config.AttrEnum;
import com.wan37.logic.monster.Monster;
import org.springframework.stereotype.Service;

@Service
public class MonsterInitializer {

    public void init(Monster monster) {
        monster.setDeadTime(0);
        monster.setAlive(true);

        long hp = monster.getAttrs().get(AttrEnum.ATTR_HP.getId()).longValue();
        monster.setHp(hp);
    }
}
