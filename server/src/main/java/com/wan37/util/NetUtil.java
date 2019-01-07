package com.wan37.util;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 消息推送类
 *
 * @author linda
 */
@Service
public class NetUtil {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void send(String msg, Set<Long> playerIds) {
        playerIds.stream()
                .filter(this::isOnline)
                .map(this::getPlayer)
                .forEach(p -> p.syncClient(msg));
    }

    private boolean isOnline(Long playerUid) {
        return playerGlobalManager.isOnline(playerUid);
    }

    private Player getPlayer(Long playerUid) {
        return playerGlobalManager.getPlayerByUid(playerUid);
    }
}
