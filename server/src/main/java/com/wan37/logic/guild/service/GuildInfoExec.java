package com.wan37.logic.guild.service;

import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPositionEnum;
import com.wan37.logic.guild.Guild;
import com.wan37.logic.guild.GuildMember;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author linda
 */
@Service
public class GuildInfoExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player) {
        if (player.getGuildUid() == null) {
            player.syncClient("你当前没有公会信息");
            return;
        }

        Guild league = guildGlobalManager.get(player.getGuildUid());
        if (league == null) {
            player.syncClient("公会不存在");
            return;
        }

        String msg = String.format("公会名：%s  人数：%s/%s\n", league.getName(), league.getCurNum(), league.getMaxNum());
        String members = league.getMembers().stream()
                .map(this::encodeMember)
                .collect(Collectors.joining("\n"));

        player.syncClient(msg + members);
    }

    private String encodeMember(GuildMember member) {
        return String.format("%s（playerUid：%s） 职位：%s （%s）", member.getName(), member.getPlayerUid(),
                GuildPositionEnum.getName(member.getPosition()), playerGlobalManager.isOnline(member.getPlayerUid()) ? "在线" : "离线");
    }
}
