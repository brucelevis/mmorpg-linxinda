package com.wan37.logic.player.service.login;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.LoginEvent;
import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerLoginExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    public void exec(PLoginPlayer loginPlayer) {
        Long playerUid = loginPlayer.getPlayerUid();
        Channel channel = loginPlayer.getChannel();

        // 登录检查
        if (playerGlobalManager.isOnline(playerUid)) {
            throw new GeneralErrorException("已经登录的角色");
        }

        Player player = playerGlobalManager.getPlayerByUid(playerUid);
        if (player == null) {
            throw new GeneralErrorException("找不到角色");
        }

        player.setChannel(channel);
        playerGlobalManager.addInOnlineList(player);

        // 触发登录事件
        generalEventListenersManager.fireEvent(new LoginEvent(player));

        String msg = "登录成功|" + playerInfoEncoder.encode(player);
        player.syncClient(msg);
    }
}
