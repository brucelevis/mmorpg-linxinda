package com.wan37.logic.pk.service;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.pk.Pk;
import com.wan37.logic.pk.init.ArenaSceneCreator;
import com.wan37.logic.pk.scene.ArenaSceneAbstract;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.SceneTypeEnum;
import com.wan37.logic.scene.TemporarySceneGlobalManager;
import com.wan37.logic.scene.config.SceneCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author linda
 */
@Service
public class PkAcceptExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private ArenaSceneCreator arenaSceneCreator;

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    @Autowired
    private SceneFacade sceneFacade;

    public void exec(Player player, Long inviterUid) {
        Pk myPk = player.getPk();
        if (!myPk.hadInvited(inviterUid)) {
            player.syncClient("对方并没邀请你决斗");
            return;
        }

        if (!playerGlobalManager.isOnline(inviterUid)) {
            myPk.rmInviter(inviterUid);
            player.syncClient("对方不在线");
            return;
        }

        Player inviter = playerGlobalManager.getPlayerByUid(inviterUid);
        Pk inviterPk = inviter.getPk();
        try {
            if (myPk.tryLock() && inviterPk.tryLock()) {
                myPk.rmInviter(inviterUid);

                if (myPk.isPking() || inviterPk.isPking()) {
                    // 有一方已经在pk了
                    player.syncClient("有一方已在决斗");
                    return;
                }

                if (!Objects.equals(player.getSceneId(), inviter.getSceneId())) {
                    player.syncClient("不在同一场景");
                    return;
                }

                SceneCfg sceneCfg = configLoader.load(SceneCfg.class, player.getSceneId())
                        .orElseThrow(() -> new RuntimeException("找不到当前场景配置"));
                if (!Objects.equals(sceneCfg.getType(), SceneTypeEnum.ORDINARY.getId())) {
                    player.syncClient("请切换到普通场景再尝试进入竞技场");
                    return;
                }

                // 标记正在决斗
                myPk.setPking(true);
                inviterPk.setPking(true);

                //FIXME: 这里先暂时写死竞技场场景配置表id
                SceneCfg arenaSceneCfg = configLoader.load(SceneCfg.class, 3001).orElse(null);
                ArenaSceneAbstract arenaScene = arenaSceneCreator.create(arenaSceneCfg);
                temporarySceneGlobalManager.addScene(arenaScene);

                // 进入竞技场开始决斗
                enterArena(inviter, arenaScene);
                enterArena(player, arenaScene);
            } else {
                player.syncClient("请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            myPk.unLock();
            inviterPk.unLock();
        }
    }

    private void enterArena(Player player, ArenaSceneAbstract arenaScene) {
        // 离开普通场景
        sceneFacade.leaveScene(player);
        player.syncClient("竞技场决斗开始");

        // 进入副本场景
        temporarySceneGlobalManager.addPlayerInScene(arenaScene.getUid(), player);
    }
}
