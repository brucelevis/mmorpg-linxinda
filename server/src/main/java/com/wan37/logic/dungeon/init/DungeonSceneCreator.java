package com.wan37.logic.dungeon.init;

import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.dungeon.schedule.DungeonSceneScheduler;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DungeonSceneCreator {

    @Autowired
    private IdTool idTool;

    @Autowired
    private DungeonMonsterCreator dungeonMonsterCreator;

    @Autowired
    private DungeonSceneScheduler dungeonSceneScheduler;

    public DungeonScene create(DungeonCfg dungeonCfg, SceneCfg sceneCfg) {
        DungeonScene scene = new DungeonScene();

        scene.setUid(idTool.generate());
        scene.setSceneCfg(sceneCfg);
        scene.setPlayers(new ArrayList<>());
        scene.setSummonings(new ArrayList<>());
        scene.setNpcs(new ArrayList<>());
        scene.setDungeonCfg(dungeonCfg);

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        scene.setExpireTime(now + TimeUnit.MINUTES.toMillis(dungeonCfg.getLimitTime()));
        scene.setDungeonSceneScheduler(dungeonSceneScheduler);

        // 开始怪物组的id为1
        scene.setMonsterGroupId(1);
        scene.setMonsters(dungeonCfg.getMonsters().get(1).getMonsters().stream()
                .map(c -> dungeonMonsterCreator.create(c, scene))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        return scene;
    }
}
