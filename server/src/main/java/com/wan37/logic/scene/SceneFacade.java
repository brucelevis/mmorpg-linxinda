package com.wan37.logic.scene;

import com.wan37.logic.player.Player;

/**
 * 门面模式：普通场景移动统一入口
 *
 * @author linda
 */
public interface SceneFacade {

    /**
     * 进入场景
     *
     * @param sceneId 场景id
     * @param player  玩家
     */
    void enterScene(Integer sceneId, Player player);

    /**
     * 场景跳转
     *
     * @param player    玩家
     * @param toSceneId 场景id
     */
    void switchScene(Player player, Integer toSceneId);

    /**
     * 强制场景跳转
     *
     * @param player 玩家
     * @param to     场景id
     */
    void forceSwitchScene(Player player, Integer to);

    /**
     * 离开场景
     *
     * @param player 玩家
     */
    void leaveScene(Player player);
}
