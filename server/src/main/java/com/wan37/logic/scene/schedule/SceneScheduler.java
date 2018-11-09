package com.wan37.logic.scene.schedule;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.config.SceneMonsterCfg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SceneScheduler {

    private static final Logger LOG = Logger.getLogger(SceneScheduler.class);

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    //@Scheduled(cron = "0/5 * *  * * ? ")
    public void schedule() {
        LOG.info(LocalDateTime.now());

        sceneGlobalManager.getAllScenes()
                .forEach(this::updateScene);
    }

    private void updateScene(Scene scene) {
        Integer sceneId = scene.getCfgId();
        SceneCfg sceneCfg = sceneCfgLoader.load(sceneId).orElse(null);
        if (sceneCfg == null) {
            return;
        }

        //TODO: 拿出场景怪物表

        //TODO: 刷出相应的缺的怪物

        List<SceneMonsterCfg> monsterCfgs = sceneCfg.getMonsters();
        List<Monster> monsters = scene.getMonsters();


        // 更新场景里的怪物
        // sceneCfg.getMonsters()
    }
}
