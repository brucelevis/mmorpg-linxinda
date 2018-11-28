package com.wan37.logic.player.listeners;

import com.wan37.event.DieEvent;
import com.wan37.event.GeneralEventListener;
import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

@Service
class PlayerOnDie implements GeneralEventListener<DieEvent> {

    @Override
    public void execute(DieEvent dieEvent) {
        FightingUnit unit = dieEvent.getUnit();
        long now = dieEvent.getNow();

        unit.setAlive(false);
        unit.setDeadTime(now);
        unit.getBuffs().clear();

        if (unit instanceof Player) {
            Player player = (Player) unit;
            player.syncClient("你已阵亡，倒计时10s传送到安全区域");
        }
    }
}
