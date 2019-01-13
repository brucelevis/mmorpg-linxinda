package com.wan37.logic.guild.service;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.LeagueJoinEvent;
import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPermissionEnum;
import com.wan37.logic.guild.GuildPositionEnum;
import com.wan37.logic.guild.config.GuildPositionCfg;
import com.wan37.logic.guild.database.GuildMemberDb;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildMember;
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
    private GuildPositionCfgLoader guildPositionCfgLoader;

    @Autowired
    private GuildMember.Factory leagueMemberFactory;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void exec(Player player, Player target) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorException("你未加入公会");
        }

        Guild league = guildGlobalManager.get(player.getLeagueUid());
        if (league == null) {
            throw new GeneralErrorException("公会不存在");
        }

        GuildMember me = league.getMember(player.getUid());
        GuildPositionCfg positionCfg = guildPositionCfgLoader.load(me.getPosition())
                .orElseThrow(() -> new GeneralErrorException("找不到公会权限表"));

        if (!positionCfg.getPermission().contains(GuildPermissionEnum.GUILD_PERMISSION_1.getId())) {
            throw new GeneralErrorException("没有添加公会成员的权限");
        }

        if (target.getLeagueUid() != null) {
            throw new GeneralErrorException("目标已有公会");
        }

        // 添加进公会
        GuildMemberDb memberDb = createMember(target.getUid());
        GuildMember leagueMember = leagueMemberFactory.create(memberDb);

        target.setLeagueUid(league.getUid());
        league.addMember(leagueMember);
        league.save();

        // 公会广播
        String msg = String.format("【公会】 恭喜[%s]加入[%s]", target.getName(), league.getName());
        league.notifyAll(msg);

        // 抛出加入公会事件
        generalEventListenersManager.fireEvent(new LeagueJoinEvent(target));
    }

    private GuildMemberDb createMember(Long playerUid) {
        GuildMemberDb memberDb = new GuildMemberDb();
        memberDb.setPlayerUid(playerUid);
        memberDb.setPosition(GuildPositionEnum.GUILD_POSITION_4.getId());
        return memberDb;
    }
}
