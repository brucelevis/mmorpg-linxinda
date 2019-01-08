package com.wan37.logic.team.entity.impl;

import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.entity.Team;
import com.wan37.logic.team.entity.TeamMember;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linda
 */
@Service
public class TeamFactoryImpl implements Team.Factory {

    @Autowired
    private TeamMember.Factory teamMemberFactory;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public Team create(Long leaderUid) {
        Team team = new TeamImpl(IdUtil.generate(), new ReentrantLock(), new HashMap<>(0), playerGlobalManager, leaderUid);
        TeamMember leader = teamMemberFactory.create(leaderUid);
        team.addMember(leader);
        return team;
    }
}
