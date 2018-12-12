package com.wan37.logic.league.entity.impl;

import com.wan37.logic.league.database.LeagueItemDb;
import com.wan37.logic.league.entity.ILeagueItem;
import org.springframework.stereotype.Service;

@Service
public class ILItemFactory implements ILeagueItem.Factory {

    @Override
    public ILeagueItem create(LeagueItemDb itemDb) {
        return new ILeagueItemImpl(itemDb);
    }
}
