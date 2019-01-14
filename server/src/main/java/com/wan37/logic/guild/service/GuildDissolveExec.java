package com.wan37.logic.guild.service;

import com.wan37.logic.chat.ChatFacade;
import com.wan37.logic.guild.GuildGlobalManager;
import com.wan37.logic.guild.dao.GuildDao;
import com.wan37.logic.guild.entity.Guild;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class GuildDissolveExec {

    @Autowired
    private GuildGlobalManager guildGlobalManager;

    @Autowired
    private ChatFacade chatFacade;

    @Autowired
    private GuildDao guildDao;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player) {
        if (player.getLeagueUid() == null) {
            player.syncClient("你未加入公会");
            return;
        }

        Guild league = guildGlobalManager.get(player.getLeagueUid());
        if (league == null) {
            player.syncClient("公会不存在");
            return;
        }

        if (!Objects.equals(league.getLeaderUid(), player.getUid())) {
            player.syncClient("你不是会长，无法解散公会");
            return;
        }

        guildDao.removeByUid(league.getUid());
        guildGlobalManager.remove(league.getUid());

        // 修改缓存玩家数据
        league.getMembers().stream()
                .map(m -> playerGlobalManager.getPlayerIfPresent(m.getPlayerUid()))
                .filter(Objects::nonNull)
                .forEach(p -> p.setLeagueUid(null));

        // 世界
        chatFacade.chatToWorld(String.format("【公告】 [%s]工会解散了", league.getName()));
    }
}
