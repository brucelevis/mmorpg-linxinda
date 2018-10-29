package com.wan37.logic.scene;

import com.wan37.logic.scene.config.SceneCfg;
import org.springframework.stereotype.Service;

@Service
class SceneFactoryImpl implements Scene.Factory {

    @Override
    public Scene create(SceneCfg cfg) {
        return new SceneImpl(cfg);
    }
}
