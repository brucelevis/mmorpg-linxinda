package com.wan37.event;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;

public class MonsterDieEvent {

    public MonsterDieEvent(Player attacker, Monster monster) {
        this.attacker = attacker;
        this.monster = monster;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Monster getMonster() {
        return monster;
    }

    private final Player attacker;
    private final Monster monster;
}
