package com.wan37.util;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 消息推送类
 */
@Service
public class NetTool {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void send(Long uid, String msg) {
        Player player = playerGlobalManager.getPlayerByUid(uid);
        if (player == null) {
            return;
        }

        player.syncClient(msg);
    }

    public void send(String msg, Set<Long> players) {
        players.stream()
                .filter(i -> playerGlobalManager.isOnline(i))
                .map(i -> playerGlobalManager.getPlayerByUid(i))
                .forEach(p -> p.syncClient(msg));
    }
}
