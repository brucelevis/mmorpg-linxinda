package com.wan37.logic.equipment.config.impl;

import com.wan37.logic.equipment.config.EquipQualityCfg;

class EquipQualityCfgImpl implements EquipQualityCfg {

    public EquipQualityCfgImpl(int score, String name) {
        this.score = score;
        this.name = name;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public String getName() {
        return name;
    }

    private final int score;
    private final String name;
}
