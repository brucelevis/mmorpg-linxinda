package com.wan37.logic.monster.listener;

import com.wan37.event.entity.DieEvent;
import com.wan37.event.GeneralEventListener;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.die.MonsterDieHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 怪物监听死亡事件
 */
@Service
class MonsterOnDie implements GeneralEventListener<DieEvent> {

    @Autowired
    private MonsterDieHandler monsterDieHandler;

    @Override
    public void execute(DieEvent dieEvent) {
        FightingUnit unit = dieEvent.getUnit();
        if (!(unit instanceof Monster)) {
            return;
        }

        Monster monster = (Monster) unit;
        long now = dieEvent.getNow();
        monsterDieHandler.handle(monster, now);
    }
}
