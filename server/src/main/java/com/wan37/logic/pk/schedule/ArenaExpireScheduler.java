package com.wan37.logic.pk.schedule;

import com.wan37.logic.pk.scene.ArenaSceneAbstract;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.TemporarySceneGlobalManager;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 竞技场过期定时器检查
 *
 * @author linda
 */
@Service
public class ArenaExpireScheduler {

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    public void schedule(ArenaSceneAbstract scene) {
        if (scene.getPlayers().size() == 0) {
            // 没人了
            temporarySceneGlobalManager.destroyScene(scene.getUid());
            return;
        }

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        if (now < scene.getExpireTime()) {
            return;
        }

        // 竞技场决斗时间结束
        scene.getPlayers().forEach(this::syncTipAndLeave);
        temporarySceneGlobalManager.destroyScene(scene.getUid());
    }

    private void syncTipAndLeave(Player player) {
        player.syncClient("平手");

        // 重置决斗标记
        player.getPk().setPking(false);

        //FIXME: 写死默认复活安全场景
        Integer toSceneId = 1000;
        sceneFacade.enterScene(toSceneId, player);
    }
}
