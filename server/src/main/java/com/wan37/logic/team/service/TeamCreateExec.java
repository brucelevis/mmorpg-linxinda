package com.wan37.logic.team.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class TeamCreateExec {

    @Autowired
    private Team.Factory teamFactory;

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    public void exec(Player player) {
        if (player.getTeamUid() != null) {
            throw new GeneralErrorException("已经有组队");
        }

        Team team = teamFactory.create(player.getUid());
        player.setTeamUid(team.getUid());

        teamGlobalManager.addTeam(team);
        player.syncClient("创建队伍成功");
    }
}
