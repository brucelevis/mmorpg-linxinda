package com.wan37.logic.scene.schedule;

import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.player.init.PlayerReviveInitializer;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class SceneScheduler {

    @Autowired
    private SceneMonsterScheduler sceneMonsterScheduler;

    @Autowired
    private SceneItemScheduler sceneItemScheduler;

    @Autowired
    private SceneMpRecoverScheduler sceneMpRecoverScheduler;

    @Autowired
    private ScenePlayerBuffsScheduler scenePlayerBuffsScheduler;

    @Autowired
    private SceneMonsterBuffsScheduler sceneMonsterBuffsScheduler;

    @Autowired
    private SceneMonsterAiScheduler sceneMonsterAiScheduler;

    @Autowired
    private PlayerReviveInitializer playerReviveInitializer;

    @Autowired
    private SceneFacade sceneFacade;

    public void schedule(Scene scene) {
        // 刷新怪物
        sceneMonsterScheduler.schedule(scene);

        // 刷新物品
        sceneItemScheduler.schedule(scene);

        // 场景mp回复
        sceneMpRecoverScheduler.schedule(scene);

        // 更新玩家buff
        scenePlayerBuffsScheduler.schedule(scene);

        // 更新怪物buff
        sceneMonsterBuffsScheduler.schedule(scene);

        // 怪物自动攻击
        sceneMonsterAiScheduler.schedule(scene);

        // 玩家复活
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        scene.getPlayers().forEach(p -> checkAndRevive(p, now));
    }

    private void checkAndRevive(Player player, long now) {
        PlayerDb playerDb = player.getPlayerDb();
        if (playerDb.isAlive()) {
            return;
        }

        long interval = TimeUnit.SECONDS.toMillis(10);
        if (playerDb.getDeadTime() + interval > now) {
            return;
        }

        Integer oldSceneId = playerDb.getSceneId();
        playerReviveInitializer.init(playerDb);

        //FIXME: 写死默认复活安全场景
        Integer toSceneId = 1000;
        sceneFacade.forceSwitchScene(player, oldSceneId, toSceneId);
    }
}
