package com.wan37.logic.monster.listener;

import com.wan37.event.DieEvent;
import com.wan37.event.GeneralEventListener;
import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.die.MonsterDieHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MonsterOnDie implements GeneralEventListener<DieEvent> {

    @Autowired
    private MonsterDieHandler monsterDieHandler;

    @Override
    public void execute(DieEvent dieEvent) {
        FightingUnit unit = dieEvent.getUnit();
        Monster monster = unit.getMonster();
        if (monster == null) {
            return;
        }

        long now = dieEvent.getNow();
        monsterDieHandler.handle(monster, now);
    }
}
