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

import java.util.Comparator;

/**
 * @author linda
 */
@Service
public class GuildChangeExec {

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
        GuildPositionCfg myPositionCfg = guildPositionCfgLoader.load(me.getPosition())
                .orElseThrow(() -> new GeneralErrorException("找不到公会权限表"));

        if (!myPositionCfg.getPermission().contains(GuildPermissionEnum.GUILD_PERMISSION_3.getId())) {
            throw new GeneralErrorException("你的职位不能改变公会成员的权限");
        }

        GuildMember targetMember = league.getMember(target.getUid());
        if (targetMember == null) {
            throw new GeneralErrorException("目标不是该公会成员");

        }

        if (me.getPosition() >= targetMember.getPosition()) {
            throw new GeneralErrorException("不能修改同职级或更高职级的人");
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
            return guildPositionCfgLoader.loads().stream()
                    .max(Comparator.comparingInt(GuildPositionCfg::getId))
                    .orElseThrow(() -> new GeneralErrorException("公会职位配置表出错"));
        }

        return guildPositionCfgLoader.load(targetPosition - 1)
                .orElseThrow(() -> new GeneralErrorException("公会职位配置表出错"));
    }
}
