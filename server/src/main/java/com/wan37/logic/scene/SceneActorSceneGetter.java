package com.wan37.logic.scene;

import com.wan37.config.ConfigLoader;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.base.SceneActor;
import com.wan37.logic.scene.config.SceneCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 场景生物获取当前场景
 *
 * @author linda
 */
@Service
public class SceneActorSceneGetter {

    @Autowired
    private ConfigLoader configLoader;

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private TemporarySceneGlobalManager temporarySceneGlobalManager;

    public AbstractScene get(SceneActor sceneActor) {
        SceneCfg sceneCfg = configLoader.load(SceneCfg.class, sceneActor.getSceneId())
                .orElseThrow(() -> new RuntimeException("找不到场景配置表"));

        if (Objects.equals(sceneCfg.getType(), SceneTypeEnum.ORDINARY.getId())) {
            // 在普通场景
            return sceneGlobalManager.querySceneById(sceneCfg.getId());
        } else {
            // 在临时场景
            return temporarySceneGlobalManager.querySceneByUid(sceneActor.getSceneUid());
        }
    }
}
