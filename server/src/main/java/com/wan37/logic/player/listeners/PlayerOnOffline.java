package com.wan37.logic.player.listeners;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.OfflineEvent;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家断线处理
 */
@Service
class PlayerOnOffline implements GeneralEventListener<OfflineEvent> {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    @Override
    public void execute(OfflineEvent offlineEvent) {
        String channelId = offlineEvent.getChannelId();
        Player player = playerGlobalManager.getPlayerByChannelId(channelId);
        if (player == null) {
            return;
        }

        playerGlobalManager.removeFromOnlineList(channelId);
        genernalEventListenersManager.fireEvent(new SceneLeaveEvent(player.getSceneId(), player.getUid()));
    }
}
