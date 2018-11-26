package com.wan37.logic.attack.fighting.impl;

import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import org.springframework.stereotype.Service;

@Service
public class FightingUnitFactoryImpl implements FightingUnit.Factory {

    @Override
    public FightingUnit create(Player player) {
        return new PlayerImpl(player);
    }

    @Override
    public FightingUnit create(Monster monster) {
        return new MonsterImpl(monster);
    }
}