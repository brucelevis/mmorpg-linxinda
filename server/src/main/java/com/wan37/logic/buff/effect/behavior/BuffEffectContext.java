package com.wan37.logic.buff.effect.behavior;

import com.wan37.logic.buff.IBuff;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;

public class BuffEffectContext {

    public BuffEffectContext(Player player, Monster monster, IBuff buff, long now) {
        this.player = player;
        this.monster = monster;
        this.buff = buff;
        this.now = now;
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return monster;
    }

    public IBuff getBuff() {
        return buff;
    }

    public long getNow() {
        return now;
    }

    private final Player player;
    private final Monster monster;
    private final IBuff buff;
    private final long now;
}
