package com.wan37.logic.league.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.LoginEvent;
import com.wan37.logic.league.LeagueGlobalManager;
import com.wan37.logic.league.entity.ILeague;
import com.wan37.logic.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class LeagueOnLogin implements GeneralEventListener<LoginEvent> {

    @Autowired
    private LeagueGlobalManager leagueGlobalManager;

    @Override
    public void execute(LoginEvent event) {
        Player player = event.getPlayer();
        if (player.getLeagueUid() == null) {
            return;
        }

        ILeague league = leagueGlobalManager.get(player.getLeagueUid());
        if (league != null) {
            return;
        }

        player.setLeagueUid(null);
        player.syncClient("你的工会已解散了");
    }
}
