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
public class TeamQuitExec {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    public void exec(Player player) {
        if (player.getTeamUid() == null) {
            throw new GeneralErrorException("你未加入组队");
        }

        Team team = teamGlobalManager.getTeam(player.getTeamUid());
        try {
            team.lock();

            player.setTeamUid(null);
            player.syncClient("你退出了组队");

            team.rmMember(player.getUid());
            String msg = String.format("[%s]退出了组队", player.getName());
            team.broadcast(msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            team.unLock();
        }
    }
}
