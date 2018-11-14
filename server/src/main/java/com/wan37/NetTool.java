package com.wan37;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
