package com.wan37.logic.team.service;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class TeamRejectExec {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    public void exec(Player player, Long teamUid) {
        Team team = teamGlobalManager.getTeam(teamUid);
        if (team == null) {
            player.syncClient("组队不存在");
            return;
        }

        Player inviter = playerGlobalManager.getPlayerByUid(team.getLeaderUid());
        player.syncClient(String.format("你拒绝了[%s]的组队邀请", inviter.getName()));

        if (playerGlobalManager.isOnline(inviter.getUid())) {
            inviter.syncClient(String.format("[%s]拒绝了你的组队邀请", player.getName()));
        }
    }
}
