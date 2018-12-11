package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.logic.player.PlayerGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ILeagueMemberFactoryImpl implements ILeagueMember.Factory {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Override
    public ILeagueMember create(LeagueMemberDb leagueMemberDb) {
        return new ILeagueMemberImpl(leagueMemberDb, playerGlobalManager);
    }
}
