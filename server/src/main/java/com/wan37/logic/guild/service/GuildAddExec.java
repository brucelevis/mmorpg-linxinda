package com.wan37.logic.guild.service;

import com.wan37.config.ConfigLoader;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.LeagueJoinEvent;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPermissionEnum;
import com.wan37.logic.guild.GuildPositionEnum;
import com.wan37.logic.guild.config.GuildPositionCfg;
import com.wan37.logic.guild.database.GuildMemberDb;
import com.wan37.logic.guild.Guild;
import com.wan37.logic.guild.GuildMember;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class GuildAddExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private GuildMember.Factory leagueMemberFactory;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void exec(Player player, Player target) {
        if (player.getGuildUid() == null) {
            player.syncClient("你未加入公会");
            return;
        }

        Guild guild = guildGlobalManager.get(player.getGuildUid());
        if (guild == null) {
            player.syncClient("公会不存在");
            return;
        }

        GuildMember memberMyself = guild.getMember(player.getUid());
        GuildPositionCfg positionCfg = configLoader.load(GuildPositionCfg.class, memberMyself.getPosition()).orElse(null);
        if (positionCfg == null) {
            player.syncClient("找不到公会权限表");
            return;
        }

        if (!positionCfg.getPermission().contains(GuildPermissionEnum.ADD_MEMBER.getId())) {
            player.syncClient("没有添加公会成员的权限");
            return;
        }

        if (target.getGuildUid() != null) {
            player.syncClient("目标已有公会");
            return;
        }

        // 添加进公会
        GuildMemberDb memberDb = createMember(target.getUid());
        GuildMember leagueMember = leagueMemberFactory.create(memberDb);

        target.setGuildUid(guild.getUid());
        guild.addMember(leagueMember);
        guild.save();

        // 公会广播
        String msg = String.format("【公会】 恭喜[%s]加入[%s]", target.getName(), guild.getName());
        guild.notifyAll(msg);

        // 抛出加入公会事件
        generalEventListenersManager.fireEvent(new LeagueJoinEvent(target));
    }

    private GuildMemberDb createMember(Long playerUid) {
        GuildMemberDb memberDb = new GuildMemberDb();
        memberDb.setPlayerUid(playerUid);
        memberDb.setPosition(GuildPositionEnum.ORDINARY.getId());
        return memberDb;
    }
}
