package com.wan37.logic.pk.schedule;

import com.wan37.logic.pk.scene.ArenaScene;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.init.PlayerReviveInitializer;
import com.wan37.logic.scene.scene.SceneFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArenaPlayerCheckerScheduler {

    @Autowired
    private PlayerReviveInitializer playerReviveInitializer;

    @Autowired
    private SceneFacade sceneFacade;

    public void schedule(ArenaScene scene) {
        // 检查死亡玩家并传送出场景
        scene.getPlayers().forEach(this::checkAndRevive);

        // 当场景只有一人就是胜利者
        if (scene.getPlayers().size() == 1) {
            Player player = scene.getPlayers().get(0);
            player.syncClient("恭喜你！决斗胜利");
            leave(player);
        }
    }

    private void checkAndRevive(Player player) {
        if (player.isAlive()) {
            return;
        }

        playerReviveInitializer.init(player);
        leave(player);
    }

    private void leave(Player player) {
        //FIXME: 写死默认复活安全场景
        Integer toSceneId = 1000;
        sceneFacade.forceSwitchScene(player, toSceneId);
    }
}
