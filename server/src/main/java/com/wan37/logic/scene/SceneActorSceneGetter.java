package com.wan37.logic.scene;

import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.SceneActor;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SceneActorSceneGetter {

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    public AbstractScene get(SceneActor sceneActor) {
        SceneCfg sceneCfg = sceneCfgLoader.load(sceneActor.getSceneId())
                .orElseThrow(() -> new RuntimeException("找不到场景配置表"));

        if (Objects.equals(sceneCfg.getType(), SceneTypeEnum.SCENE_TYPE_1.getId())) {
            // 在普通场景
            return sceneGlobalManager.querySceneById(sceneCfg.getId());
        } else {
            // 在临时场景
            return temporarySceneGlobalManager.querySceneByUid(sceneActor.getSceneUid());
        }
    }
}
