package com.wan37.logic.scene.init;

import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SceneCreator {

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    public Scene create(Integer sceneId) {
        SceneCfg sceneCfg = sceneCfgLoader.load(sceneId)
                .orElseThrow(() -> new RuntimeException("找不到SceneCfg"));

        Scene scene = new Scene();
        scene.setSceneCfg(sceneCfg);
        scene.setPlayers(new ArrayList<>());

        return scene;
    }
}
