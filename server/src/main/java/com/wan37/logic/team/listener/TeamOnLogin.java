package com.wan37.logic.team.listener;

import com.wan37.event.GeneralEventListener;
import com.wan37.event.entity.LoginEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 组队监听登陆事件
 */
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

        Team team = teamGlobalManager.getTeam(player.getTeamUid());
        if (team != null && team.getMember(player.getUid()) != null) {
            // 组队存在且还在队伍里
            return;
        }

        player.setTeamUid(null);
    }
}
