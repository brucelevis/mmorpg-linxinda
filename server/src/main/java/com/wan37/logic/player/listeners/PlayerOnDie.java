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

        if (!(unit instanceof Player)) {
            return;
        }

        Player player = (Player) unit;
        player.setAlive(false);
        player.setDeadTime(now);
        player.getBuffs().clear();

        player.syncClient("你已阵亡，倒计时10s传送到安全区域");
    }
}
