package com.wan37.logic.team.entity.impl;

import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.team.entity.TeamMember;

class TeamMemberImpl implements TeamMember {

    public TeamMemberImpl(Long playerUid, PlayerGlobalManager playerGlobalManager) {
        this.playerUid = playerUid;
        this.playerGlobalManager = playerGlobalManager;
    }

    @Override
    public Long getPlayerUid() {
        return playerUid;
    }

    @Override
    public boolean isOnline() {
        return playerGlobalManager.isOnline(playerUid);
    }

    private final Long playerUid;
    private final PlayerGlobalManager playerGlobalManager;
}
