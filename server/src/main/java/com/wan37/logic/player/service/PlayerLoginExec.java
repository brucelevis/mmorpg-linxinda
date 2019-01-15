package com.wan37.logic.player.service;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.LoginEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class PlayerLoginExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    public void exec(Long playerUid, Channel channel) {
        // 登录检查
        if (playerGlobalManager.isOnline(playerUid)) {
            channel.writeAndFlush("已经登录的角色" + "\n");
            return;
        }

        Player player = playerGlobalManager.getPlayerByUid(playerUid);
        if (player == null) {
            channel.writeAndFlush("找不到角色" + "\n");
            return;
        }

        player.setChannel(channel);
        playerGlobalManager.addInOnlineList(player);

        // 触发登录事件
        generalEventListenersManager.fireEvent(new LoginEvent(player));

        String msg = "登录成功|" + playerInfoEncoder.encode(player);
        player.syncClient(msg);
    }
}
