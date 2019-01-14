package com.wan37.logic.guild.service;

import com.wan37.config.ConfigLoader;
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
    private ConfigLoader configLoader;

    public void exec(Player player, Player target) {
        if (player.getGuildUid() == null) {
            player.syncClient("你未加入公会");
            return;
        }

        Guild league = guildGlobalManager.get(player.getGuildUid());
        if (league == null) {
            player.syncClient("公会不存在");
            return;
        }

        GuildMember me = league.getMember(player.getUid());
        GuildPositionCfg positionCfg = configLoader.load(GuildPositionCfg.class, me.getPosition()).orElse(null);
        if (positionCfg == null) {
            player.syncClient("找不到公会权限表");
            return;
        }

        if (!positionCfg.getPermission().contains(GuildPermissionEnum.GUILD_PERMISSION_2.getId())) {
            player.syncClient("没有踢除公会成员的权限");
            return;
        }

        GuildMember targetMember = league.getMember(target.getUid());
        if (targetMember == null) {
            player.syncClient("目标不是该公会成员");
            return;
        }

        if (me.getPosition() >= targetMember.getPosition()) {
            player.syncClient("不能踢除同职级或更高职级的人");
            return;
        }

        // 广播
        String msg = String.format("【公会】 [%s]踢除了[%s]", player.getName(), target.getName());
        league.notifyAll(msg);

        target.setGuildUid(null);
        league.rmMember(target.getUid());
        league.save();
    }
}
