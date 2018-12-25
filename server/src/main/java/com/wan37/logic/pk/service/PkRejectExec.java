package com.wan37.logic.pk.service;

import com.wan37.logic.pk.entity.IPk;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PkRejectExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player, Long uid) {
        IPk iPk = player.getPk();
        if (!iPk.hadInvited(uid)) {
            // 没在邀请队列
            return;
        }

        iPk.rmInviter(uid);
        player.syncClient("你拒绝了决斗邀请");

        if (playerGlobalManager.isOnline(uid)) {
            Player inviter = playerGlobalManager.getPlayerByUid(uid);
            inviter.syncClient(String.format("[%s]拒绝了你的决斗邀请", player.getName()));
        }
    }
}
