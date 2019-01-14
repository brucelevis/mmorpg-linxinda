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

import java.util.Comparator;

/**
 * @author linda
 */
@Service
public class GuildChangeExec {

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
        GuildPositionCfg myPositionCfg = configLoader.load(GuildPositionCfg.class, me.getPosition()).orElse(null);
        if (myPositionCfg == null) {
            player.syncClient("找不到公会权限表");
            return;
        }

        if (!myPositionCfg.getPermission().contains(GuildPermissionEnum.GUILD_PERMISSION_3.getId())) {
            player.syncClient("你的职位不能改变公会成员的权限");
            return;
        }

        GuildMember targetMember = league.getMember(target.getUid());
        if (targetMember == null) {
            player.syncClient("目标不是该公会成员");
            return;
        }

        if (me.getPosition() >= targetMember.getPosition()) {
            player.syncClient("不能修改同职级或更高职级的人");
            return;
        }

        GuildPositionCfg newPositionCfg = getNewPositionCfg(me.getPosition(), targetMember.getPosition());
        targetMember.setPosition(newPositionCfg.getId());
        league.save();

        // 广播
        String msg = String.format("【公会】 [%s]将[%s]的职位调整为[%s]", player.getName(), target.getName(), newPositionCfg.getName());
        league.notifyAll(msg);
    }

    private GuildPositionCfg getNewPositionCfg(Integer myPosition, Integer targetPosition) {
        if (myPosition == targetPosition - 1) {
            return configLoader.loads(GuildPositionCfg.class).stream()
                    .max(Comparator.comparingInt(GuildPositionCfg::getId))
                    .orElseThrow(() -> new RuntimeException("公会职位配置表出错"));
        }

        return configLoader.load(GuildPositionCfg.class, targetPosition - 1)
                .orElseThrow(() -> new RuntimeException("公会职位配置表出错"));
    }
}
