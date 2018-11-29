package com.wan37.logic.player.listeners;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.OfflineEvent;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
import io.netty.channel.Channel;
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

    @Autowired
    private PlayerDao playerDao;

    @Override
    public void execute(OfflineEvent offlineEvent) {
        Channel channel = offlineEvent.getChannel();
        Player player = playerGlobalManager.getPlayerByChannel(channel);
        if (player == null) {
            return;
        }

        // 玩家数据持久化
        playerDao.save(player.getPlayerDb());

        playerGlobalManager.removeFromOnlineList(channel);
        genernalEventListenersManager.fireEvent(new SceneLeaveEvent(player));
    }
}
