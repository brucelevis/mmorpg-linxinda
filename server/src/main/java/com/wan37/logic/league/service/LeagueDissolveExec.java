package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.chat.ChatFacade;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.dao.LeagueDao;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LeagueDissolveExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Autowired
    private ChatFacade chatFacade;

    @Autowired
    private LeagueDao leagueDao;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("你未加入公会");
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        if (league == null) {
            throw new GeneralErrorExecption("公会不存在");
        }

        if (!Objects.equals(league.getLeaderUid(), player.getUid())) {
            throw new GeneralErrorExecption("你不是会长，无法解散公会");
        }

        leagueDao.removeByUid(league.getUid());
        leagueGlobalManager.remove(league.getUid());

        // 修改缓存玩家数据
        league.getMembers().stream()
                .map(m -> playerGlobalManager.getPlayerIfPresent(m.getPlayerUid()))
                .filter(Objects::nonNull)
                .forEach(p -> p.setLeagueUid(null));

        // 世界
        chatFacade.chatToWorld(String.format("【公告】 [%s]工会解散了", league.getName()));
    }
}
