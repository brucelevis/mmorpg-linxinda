package com.wan37.logic.team.service;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.event.TeamJoinEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.Team;
import com.wan37.logic.team.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class TeamAcceptExec {

    /**
     * FIXME:写死组队上限人数
     */
    private static final int TEAM_MAX_NUM = 6;

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private TeamMember.Factory teamMemberFactory;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void exec(Player player, Long teamUid) {
        if (player.getTeamUid() != null) {
            player.syncClient("你已有组队");
            return;
        }

        Team team = teamGlobalManager.getTeam(teamUid);
        if (team == null) {
            player.syncClient("组队不存在");
            return;
        }

        try {
            team.lock();
            if (team.getMembers().size() >= TEAM_MAX_NUM) {
                player.syncClient("组队人数已满");
                return;
            }

            // 推送
            String msg = String.format("[%s]加入了队伍", player.getName());
            team.broadcast(msg);

            TeamMember member = teamMemberFactory.create(player.getUid());
            team.addMember(member);

            player.setTeamUid(teamUid);
            player.syncClient("你加入了组队");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            team.unLock();
        }

        // 抛出加入组队事件
        generalEventListenersManager.fireEvent(new TeamJoinEvent(player));
    }
}
