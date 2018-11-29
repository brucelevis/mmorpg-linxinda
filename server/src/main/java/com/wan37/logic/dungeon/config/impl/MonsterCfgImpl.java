package com.wan37.logic.dungeon.config.impl;

import com.wan37.logic.dungeon.config.DungeonMonsterCfg;

class MonsterCfgImpl implements DungeonMonsterCfg {

    public MonsterCfgImpl(Integer id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public Integer getMonsterId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    private final Integer id;
    private final int amount;
}
