package com.wan37.logic.team.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class TeamKickExec {

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    public void exec(Player player, Player target) {
        if (player.getTeamUid() == null) {
            throw new GeneralErrorException("你未创建组队");
        }

        Team team = teamGlobalManager.getTeam(player.getTeamUid());
        if (!Objects.equals(team.getLeaderUid(), player.getUid())) {
            throw new GeneralErrorException("你不是队长，无法踢除成员");
        }

        try {
            team.lock();

            if (team.getMember(target.getUid()) == null) {
                player.syncClient("目标不是组队成员，无法踢除");
                return;
            }

            team.rmMember(target.getUid());
            String msg = String.format("[%s]被踢出组队", target.getName());
            team.broadcast(msg);

            target.setTeamUid(null);
            target.syncClient("你被踢出组队");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            team.unLock();
        }
    }
}
