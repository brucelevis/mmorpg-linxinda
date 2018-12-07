package com.wan37.logic.league.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.database.LeagueRootDb;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueInfoExec {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    public void exec(Player player) {
        if (player.getLeagueUid() == null) {
            throw new GeneralErrorExecption("你当前没有公会信息");
        }

        LeagueRootDb leagueRootDb = leagueGlobalManager.getLeague(player.getLeagueUid());
        if (leagueRootDb == null) {
            player.setLeagueUid(null);
            throw new GeneralErrorExecption("你当前没有公会信息");
        }

        String msg = String.format("公会名：%s，人数：%s/%s", leagueRootDb.getName(),
                leagueRootDb.getMembers().size(), leagueRootDb.getMaxNum());
        player.syncClient(msg);
    }
}
