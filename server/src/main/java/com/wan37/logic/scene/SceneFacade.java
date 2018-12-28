package com.wan37.logic.scene;

import com.wan37.logic.player.Player;

public interface SceneFacade {

    void enterScene(Integer sceneId, Player player);

    void switchScene(Player player, Integer toSceneId);

    void forceSwitchScene(Player player, Integer to);

    void leaveScene(Player player);
}
