package com.wan37.logic.attack.fighting.impl;

import com.wan37.logic.attack.fighting.FightingSkill;
import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;

import java.util.Map;

class MonsterImpl implements FightingUnit {

    public MonsterImpl(Monster monster) {
        this.monster = monster;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public Monster getMonster() {
        return monster;
    }

    @Override
    public boolean isDie() {
        return false;
    }

    @Override
    public long getHp() {
        return monster.getHp();
    }

    @Override
    public void setHp(long hp) {
        monster.setHp(hp);
    }

    @Override
    public long getMp() {
        return 0;
    }

    @Override
    public void setMp(long mp) {
        // NOOP
    }

    @Override
    public Map<Integer, Double> getAttrs() {
        return monster.getAttrs();
    }

    @Override
    public Map<Integer, FightingSkill> getSkills() {
        return null;
    }

    @Override
    public void client(String msg) {

    }

    private final Monster monster;
}
