package com.wan37.logic.player.listeners;

import com.wan37.event.DieEvent;
import com.wan37.event.GeneralEventListener;
import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import org.springframework.stereotype.Service;

@Service
class PlayerOnDie implements GeneralEventListener<DieEvent> {

    @Override
    public void execute(DieEvent dieEvent) {
        FightingUnit unit = dieEvent.getUnit();
        Player player = unit.getPlayer();
        if (player == null) {
            return;
        }

        long now = dieEvent.getNow();
        PlayerDb playerDb = player.getPlayerDb();
        playerDb.setAlive(false);
        playerDb.setDeadTime(now);

        player.syncClient("你已阵亡，倒计时10s传送到安全区域");
    }
}
