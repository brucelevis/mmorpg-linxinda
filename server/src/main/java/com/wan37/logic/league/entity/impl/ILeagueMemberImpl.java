package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueMemberDb;
import com.wan37.logic.league.entity.ILeagueMember;
import com.wan37.logic.player.PlayerGlobalManager;

class ILeagueMemberImpl implements ILeagueMember {

    public ILeagueMemberImpl(LeagueMemberDb leagueMemberDb, PlayerGlobalManager playerGlobalManager) {
        this.leagueMemberDb = leagueMemberDb;
        this.playerGlobalManager = playerGlobalManager;
    }

    @Override
    public LeagueMemberDb getLeagueMemberDb() {
        return leagueMemberDb;
    }

    @Override
    public Long getPlayerUid() {
        return leagueMemberDb.getPlayerUid();
    }

    @Override
    public Integer getPosition() {
        return leagueMemberDb.getPosition();
    }

    @Override
    public void setPosition(Integer position) {
        leagueMemberDb.setPosition(position);
    }

    @Override
    public String getName() {
        return playerGlobalManager.getPlayerByUid(leagueMemberDb.getPlayerUid()).getName();
    }

    private final LeagueMemberDb leagueMemberDb;
    private final PlayerGlobalManager playerGlobalManager;
}
