package com.wan37.logic.team.entity.impl;

import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.entity.ITeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ITeamMemberFactoryImpl implements ITeamMember.Factory {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public ITeamMember create(Long playerUid) {
        return new ITeamMemberImpl(playerUid, playerGlobalManager);
    }
}
