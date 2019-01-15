package com.wan37.logic.guild.service;

import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.Guild;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class GuildQuitExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    public void exec(Player player) {
        if (player.getGuildUid() == null) {
            player.syncClient("你未加入公会");
            return;
        }

        Guild league = guildGlobalManager.get(player.getGuildUid());
        if (league == null) {
            player.syncClient("公会不存在");
            return;
        }

        if (Objects.equals(league.getLeaderUid(), player.getUid())) {
            player.syncClient("不允许创建者退出公会");
            return;
        }

        String msg = String.format("【公会】 [%s]退出了公会", player.getName());
        league.notifyAll(msg);

        player.setGuildUid(null);
        league.rmMember(player.getUid());
        league.save();
    }
}
