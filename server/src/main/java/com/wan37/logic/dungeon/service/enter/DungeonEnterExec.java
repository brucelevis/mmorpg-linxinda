package com.wan37.logic.dungeon.service.enter;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.config.DungeonCfgLoader;
import com.wan37.logic.dungeon.init.DungeonSceneCreator;
import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.player.Player;
import com.wan37.logic.scene.base.SceneTypeEnum;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.scene.SceneFacade;
import com.wan37.logic.scene.temporary.TemporarySceneGlobalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DungeonEnterExec {

    @Autowired
    private DungeonCfgLoader dungeonCfgLoader;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private SceneFacade sceneFacade;

    @Autowired
    private DungeonSceneCreator dungeonSceneCreator;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    public void exec(Player player, Integer dungeonId) {
        SceneCfg sceneCfg = sceneCfgLoader.load(player.getSceneId())
                .orElseThrow(() -> new GeneralErrorExecption("找不到当前场景配置"));

        if (!Objects.equals(sceneCfg.getType(), SceneTypeEnum.SCENE_TYPE_1.getId())) {
            throw new GeneralErrorExecption("请切换到普通场景再尝试进入副本");
        }

        DungeonCfg dungeonCfg = dungeonCfgLoader.load(dungeonId)
                .orElseThrow(() -> new GeneralErrorExecption("找不到相应的副本"));

        SceneCfg dungeonSceneCfg = sceneCfgLoader.load(dungeonCfg.getSceneId())
                .orElseThrow(() -> new GeneralErrorExecption("找不到副本场景配置"));

        // 创建副本场景
        DungeonScene dungeonScene = dungeonSceneCreator.create(dungeonCfg, dungeonSceneCfg);
        temporarySceneGlobalManager.addScene(dungeonScene);

        // 离开普通场景
        sceneFacade.leaveScene(player);

        String msg = String.format("[%s副本]挑战开始\n", dungeonCfg.getName());
        player.syncClient(msg);

        // 进入副本场景
        temporarySceneGlobalManager.addPlayerInScene(dungeonScene.getUid(), player);
    }
}
