package com.wan37.logic.guild.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.LoginEvent;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公会系统监听登录事件
 */
@Service
class GuildOnLogin implements GeneralEventListener<LoginEvent> {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Override
    public void execute(LoginEvent event) {
        Player player = event.getPlayer();
        if (player.getLeagueUid() == null) {
            return;
        }

        Guild league = guildGlobalManager.get(player.getLeagueUid());
        if (league != null) {
            return;
        }

        player.setLeagueUid(null);
        player.syncClient("你的工会已解散了");
    }
}
