package com.wan37.logic.guild.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPositionEnum;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildMember;
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
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("你当前没有公会信息");
        }

        Guild league = guildGlobalManager.get(player.getLeagueUid());
        if (league == null) {
            throw new GeneralErrorException("公会不存在");
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
