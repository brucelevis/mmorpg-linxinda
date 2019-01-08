package com.wan37.logic.pk.schedule;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.PkWinEvent;
import com.wan37.logic.pk.scene.ArenaSceneAbstract;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.init.PlayerReviveInitializer;
import com.wan37.logic.scene.SceneFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 竞技场玩家状态检查定时器
 *
 * @author linda
 */
@Service
public class ArenaPlayerCheckerScheduler {

    @Autowired
    private PlayerReviveInitializer playerReviveInitializer;

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void schedule(ArenaSceneAbstract scene) {
        // 检查死亡玩家并传送出场景
        scene.getPlayers().forEach(this::checkAndRevive);

        // 当场景只有一人就是胜利者
        if (scene.getPlayers().size() == 1) {
            Player player = scene.getPlayers().get(0);
            player.syncClient("恭喜你！决斗胜利");
            resetPkAndLeave(player);

            // 抛出决斗胜利事件
            generalEventListenersManager.fireEvent(new PkWinEvent(player));
        }
    }

    private void checkAndRevive(Player player) {
        if (player.isAlive()) {
            return;
        }

        playerReviveInitializer.init(player);
        resetPkAndLeave(player);
    }

    private void resetPkAndLeave(Player player) {
        // 重置决斗标记
        player.getPk().setPking(false);

        //FIXME: 写死默认复活安全场景
        Integer toSceneId = 1000;
        sceneFacade.forceSwitchScene(player, toSceneId);
    }
}
