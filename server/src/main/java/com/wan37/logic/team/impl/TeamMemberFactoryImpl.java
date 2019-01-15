package com.wan37.logic.team.impl;

import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class TeamMemberFactoryImpl implements TeamMember.Factory {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public TeamMember create(Long playerUid) {
        return new TeamMemberImpl(playerUid, playerGlobalManager);
    }
}
