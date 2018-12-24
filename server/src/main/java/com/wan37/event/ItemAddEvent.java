package com.wan37.event;

import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;

public class ItemAddEvent {

    public ItemAddEvent(PropsCfg propsCfg, int amount, Player player) {
        this.propsCfg = propsCfg;
        this.amount = amount;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public PropsCfg getPropsCfg() {
        return propsCfg;
    }

    public int getAmount() {
        return amount;
    }

    private final PropsCfg propsCfg;
    private final int amount;
    private final Player player;
}
