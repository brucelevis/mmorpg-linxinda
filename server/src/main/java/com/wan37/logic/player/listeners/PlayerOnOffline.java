package com.wan37.logic.player.listeners;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.OfflineEvent;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.dao.PlayerDao;
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
        Player player = offlineEvent.getPlayer();

        // 玩家数据持久化
        playerDao.save(player.getPlayerDb());

        playerGlobalManager.removeFromOnlineList(player.getChannel());
        genernalEventListenersManager.fireEvent(new SceneLeaveEvent(player));
    }
}
