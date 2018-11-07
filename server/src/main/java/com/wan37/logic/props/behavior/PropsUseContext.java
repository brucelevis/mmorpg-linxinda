package com.wan37.logic.props.behavior;

import com.wan37.logic.player.Player;
import com.wan37.logic.props.config.PropsCfg;

public class PropsUseContext {

    public PropsUseContext(Player player, PropsCfg propsCfg) {
        this.player = player;
        this.propsCfg = propsCfg;
    }

    public Player getPlayer() {
        return player;
    }

    public PropsCfg getPropsCfg() {
        return propsCfg;
    }

    private final Player player;
    private final PropsCfg propsCfg;
}
