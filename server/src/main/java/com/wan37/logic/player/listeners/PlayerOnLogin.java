package com.wan37.logic.player.listeners;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.LoginEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.init.PlayerReviveInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PlayerOnLogin implements GeneralEventListener<LoginEvent> {

    @Autowired
    private PlayerReviveInitializer playerReviver;

    @Override
    public void execute(LoginEvent event) {
        Player player = event.getPlayer();
        if (!player.isAlive()) {
            // 复活
            playerReviver.init(player);
        }
    }
}
