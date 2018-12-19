package com.wan37.logic.team.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.LoginEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.ITeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TeamOnLogin implements GeneralEventListener<LoginEvent> {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Override
    public void execute(LoginEvent event) {
        Player player = event.getPlayer();
        if (player.getTeamUid() == null) {
            return;
        }

        ITeam team = teamGlobalManager.getTeam(player.getTeamUid());
        if (team != null) {
            return;
        }

        player.setTeamUid(null);
    }
}
