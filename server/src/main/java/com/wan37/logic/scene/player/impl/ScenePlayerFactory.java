package com.wan37.logic.scene.player.impl;

import com.wan37.logic.player.Player;
import com.wan37.logic.scene.player.ScenePlayer;
import org.springframework.stereotype.Service;

@Service
class ScenePlayerFactory implements ScenePlayer.Factory {

    @Override
    public ScenePlayer create(Player player) {
        return new PlayerImpl(player);
    }
}
