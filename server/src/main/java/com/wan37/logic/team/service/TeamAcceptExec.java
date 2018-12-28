package com.wan37.logic.team.service;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.entity.TeamJoinEvent;
import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.player.Player;
import com.wan37.logic.team.TeamGlobalManager;
import com.wan37.logic.team.entity.ITeam;
import com.wan37.logic.team.entity.ITeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamAcceptExec {

    //FIXME: 写死组队上限人数
    private static final int TEAM_MAX_NUM = 6;

    @Autowired
    private TeamGlobalManager teamGlobalManager;

    @Autowired
    private ITeamMember.Factory teamMemberFactory;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public void exec(Player player, Long teamUid) {
        if (player.getTeamUid() != null) {
            throw new GeneralErrorExecption("你已有组队");
        }

        ITeam team = teamGlobalManager.getTeam(teamUid);
        if (team == null) {
            throw new GeneralErrorExecption("组队不存在");
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

            ITeamMember member = teamMemberFactory.create(player.getUid());
            team.addMember(member);

            player.setTeamUid(teamUid);
            player.syncClient("你加入了组队");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            team.unLock();
        }

        // 抛出加入组队事件
        genernalEventListenersManager.fireEvent(new TeamJoinEvent(player));
    }
}
