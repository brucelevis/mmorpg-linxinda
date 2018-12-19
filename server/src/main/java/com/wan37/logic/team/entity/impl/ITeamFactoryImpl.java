package com.wan37.logic.team.entity.impl;

import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.entity.ITeam;
import com.wan37.logic.team.entity.ITeamMember;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ITeamFactoryImpl implements ITeam.Factory {

    @Autowired
    private IdTool idTool;

    @Autowired
    private ITeamMember.Factory teamMemberFactory;

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public ITeam create(Long leaderUid) {
        ITeam team = new ITeamImpl(idTool.generate(), new ReentrantLock(), new HashMap<>(), playerGlobalManager, leaderUid);
        ITeamMember leader = teamMemberFactory.create(leaderUid);
        team.addMember(leader);
        return team;
    }
}
