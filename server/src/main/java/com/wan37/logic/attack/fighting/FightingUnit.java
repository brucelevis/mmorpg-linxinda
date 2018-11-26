package com.wan37.logic.attack.fighting;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;

import java.util.Map;

public interface FightingUnit {

    interface Factory {

        FightingUnit create(Player player);

        FightingUnit create(Monster monster);
    }

    Player getPlayer();

    Monster getMonster();

    boolean isDie();

    long getHp();

    void setHp(long hp);

    long getMp();

    void setMp(long mp);

    Map<Integer, Double> getAttrs();

    void client(String msg);
}
