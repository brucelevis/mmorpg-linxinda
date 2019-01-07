package com.wan37.logic.pk.service;

import com.wan37.exception.GeneralErrorException;
import com.wan37.logic.pk.entity.IPk;
import com.wan37.logic.pk.init.ArenaSceneCreator;
import com.wan37.logic.pk.scene.ArenaScene;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import com.wan37.logic.scene.SceneTypeEnum;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.SceneFacade;
import com.wan37.logic.scene.TemporarySceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PkAcceptExec {

    @Autowired
    private PlayerGlobalManager playerGlobalManager;

    @Autowired
    private ArenaSceneCreator arenaSceneCreator;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    @Autowired
    private SceneFacade sceneFacade;

    public void exec(Player player, Long inviterUid) {
        IPk myPk = player.getPk();
        if (!myPk.hadInvited(inviterUid)) {
            throw new GeneralErrorException("对方并没邀请你决斗");
        }

        if (!playerGlobalManager.isOnline(inviterUid)) {
            myPk.rmInviter(inviterUid);
            player.syncClient("对方不在线");
            return;
        }

        Player inviter = playerGlobalManager.getPlayerByUid(inviterUid);
        IPk inviterPk = inviter.getPk();
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

                SceneCfg sceneCfg = sceneCfgLoader.load(player.getSceneId())
                        .orElseThrow(() -> new RuntimeException("找不到当前场景配置"));
                if (!Objects.equals(sceneCfg.getType(), SceneTypeEnum.SCENE_TYPE_1.getId())) {
                    player.syncClient("请切换到普通场景再尝试进入竞技场");
                    return;
                }

                // 标记正在决斗
                myPk.setPking(true);
                inviterPk.setPking(true);

                //FIXME: 这里先暂时写死竞技场场景配置表id
                SceneCfg arenaSceneCfg = sceneCfgLoader.load(3001).orElse(null);
                ArenaScene arenaScene = arenaSceneCreator.create(arenaSceneCfg);
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

    private void enterArena(Player player, ArenaScene arenaScene) {
        // 离开普通场景
        sceneFacade.leaveScene(player);
        player.syncClient("竞技场决斗开始");

        // 进入副本场景
        temporarySceneGlobalManager.addPlayerInScene(arenaScene.getUid(), player);
    }
}
