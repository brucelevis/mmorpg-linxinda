package com.wan37.logic.attack.fighting.impl;

import com.wan37.logic.attack.fighting.FightingSkill;
import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;

import java.util.Map;

class PlayerImpl implements FightingUnit {

    public PlayerImpl(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Monster getMonster() {
        return null;
    }

    @Override
    public boolean isDie() {
        return false;
    }

    @Override
    public long getHp() {
        return player.getPlayerDb().getHp();
    }

    @Override
    public void setHp(long hp) {
        player.getPlayerDb().setHp(hp);
    }

    @Override
    public long getMp() {
        return player.getPlayerDb().getMp();
    }

    @Override
    public void setMp(long mp) {
        player.getPlayerDb().setMp(mp);
    }

    @Override
    public Map<Integer, Double> getAttrs() {
        return player.getPlayerDb().getPlayerStrengthDb().getAttrs();
    }

    @Override
    public Map<Integer, FightingSkill> getSkills() {
        return null;
    }

    @Override
    public void client(String msg) {

    }

    private final Player player;
}
