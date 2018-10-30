package com.wan37.logic.player.listeners;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.OfflineEvent;
import org.springframework.stereotype.Service;

@Service
class PlayerOnOffline implements GeneralEventListener<OfflineEvent> {

    @Override
    public void execute(OfflineEvent offlineEvent) {
        // NOOP
    }
}
