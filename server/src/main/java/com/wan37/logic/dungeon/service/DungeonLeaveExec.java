package com.wan37.logic.dungeon.service;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.TemporarySceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linda
 */
@Service
public class DungeonLeaveExec {

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void exec(Player player) {
        // 离开副本场景
        generalEventListenersManager.fireEvent(new SceneLeaveEvent(player));
        temporarySceneGlobalManager.destoryScene(player.getSceneUid());

        // 回到安全村
        sceneFacade.enterScene(1000, player);
    }
}
