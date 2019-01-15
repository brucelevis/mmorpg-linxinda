package com.wan37.logic.guild.impl;

import com.wan37.logic.guild.database.GuildItemDb;
import com.wan37.logic.guild.GuildItem;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class GuildItemFactory implements GuildItem.Factory {

    @Override
    public GuildItem create(GuildItemDb itemDb) {
        return new GuildItemImpl(itemDb);
    }
}
