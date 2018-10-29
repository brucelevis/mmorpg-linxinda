package com.wan37.logic.scene.player;

import com.wan37.logic.player.Player;

public interface ScenePlayer {

    interface Factory {

        ScenePlayer create(Player player);
    }

    Long getPlayerUid();
}
