package com.wan37.logic.dungeon.service.leave;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.SceneLeaveEvent;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.scene.SceneFacade;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DungeonLeaveExec {

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public void exec(Player player) {
        // 离开副本场景
        genernalEventListenersManager.fireEvent(new SceneLeaveEvent(player));
        temporarySceneGlobalManager.destoryScene(player.getSceneUid());

        // 回到安全村
        sceneFacade.enterScene(1000, player);
    }
}
