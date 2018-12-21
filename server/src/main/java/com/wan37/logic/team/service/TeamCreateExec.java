package com.wan37.logic.team.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.ITeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamCreateExec {

    @Autowired
    private ITeam.Factory teamFactory;

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    public void exec(Player player) {
        if (player.getTeamUid() != null) {
            throw new GeneralErrorExecption("已经有组队");
        }

        ITeam team = teamFactory.create(player.getUid());
        player.setTeamUid(team.getUid());

        teamGlobalManager.addTeam(team);
        player.syncClient("创建队伍成功");
    }
}