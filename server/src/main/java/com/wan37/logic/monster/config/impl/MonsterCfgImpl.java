package com.wan37.logic.monster.config.impl;

import com.wan37.config.entity.MonsterCfgExcel;
import com.wan37.logic.monster.config.MonsterCfg;

public class MonsterCfgImpl implements MonsterCfg {

    public MonsterCfgImpl(MonsterCfgExcel monsterCfgExcel) {
        this.monsterCfgExcel = monsterCfgExcel;
    }

    @Override
    public Integer getId() {
        return monsterCfgExcel.getId();
    }

    @Override
    public String getName() {
        return monsterCfgExcel.getName();
    }

    private final MonsterCfgExcel monsterCfgExcel;
}
