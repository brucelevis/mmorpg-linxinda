package com.wan37.logic.pk.init;

import com.wan37.logic.pk.scene.ArenaScene;
import com.wan37.logic.pk.schedule.ArenaSceneScheduler;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class ArenaSceneCreator {

    @Autowired
    private IdTool idTool;

    @Autowired
    private ArenaSceneScheduler arenaSceneScheduler;

    public ArenaScene create(SceneCfg sceneCfg) {
        ArenaScene scene = new ArenaScene();

        scene.setUid(idTool.generate());
        scene.setSceneCfg(sceneCfg);
        scene.setPlayers(new ArrayList<>());
        scene.setSummonings(new ArrayList<>());

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        //FIXME: 写死决斗时间
        scene.setExpireTime(now + TimeUnit.MINUTES.toMillis(2));
        scene.setArenaSceneScheduler(arenaSceneScheduler);

        scene.setNpcs(new ArrayList<>());
        scene.setMonsters(new ArrayList<>());
        return scene;
    }
}
