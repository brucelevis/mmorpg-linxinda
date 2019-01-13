package com.wan37.logic.guild.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.GuildPermissionEnum;
import com.wan37.logic.guild.config.GuildPositionCfg;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.guild.entity.GuildMember;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class GuildKickExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private GuildPositionCfgLoader guildPositionCfgLoader;

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

        if (!positionCfg.getPermission().contains(GuildPermissionEnum.GUILD_PERMISSION_2.getId())) {
            throw new GeneralErrorException("没有踢除公会成员的权限");
        }

        GuildMember targetMember = league.getMember(target.getUid());
        if (targetMember == null) {
            throw new GeneralErrorException("目标不是该公会成员");
        }

        if (me.getPosition() >= targetMember.getPosition()) {
            throw new GeneralErrorException("不能踢除同职级或更高职级的人");
        }

        // 广播
        String msg = String.format("【公会】 [%s]踢除了[%s]", player.getName(), target.getName());
        league.notifyAll(msg);

        target.setLeagueUid(null);
        league.rmMember(target.getUid());
        league.save();
    }
}
