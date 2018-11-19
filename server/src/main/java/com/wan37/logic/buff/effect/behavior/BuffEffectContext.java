package com.wan37.logic.buff.effect.behavior;

import com.wan37.logic.buff.IBuff;
import com.wan37.logic.player.Player;

public class BuffEffectContext {

    public BuffEffectContext(Player player, IBuff buff, long now) {
        this.player = player;
        this.buff = buff;
        this.now = now;
    }

    public Player getPlayer() {
        return player;
    }

    public IBuff getBuff() {
        return buff;
    }

    public long getNow() {
        return now;
    }

    private final Player player;
    private final IBuff buff;
    private final long now;
}
