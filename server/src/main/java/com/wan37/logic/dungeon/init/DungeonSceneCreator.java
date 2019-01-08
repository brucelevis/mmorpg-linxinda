package com.wan37.logic.dungeon.init;

import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.scene.DungeonSceneAbstract;
import com.wan37.logic.dungeon.schedule.DungeonSceneSchedulerAbstract;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 副本场景生成类
 *
 * @author linda
 */
@Service
public class DungeonSceneCreator {

    @Autowired
    private DungeonMonsterCreator dungeonMonsterCreator;

    @Autowired
    private DungeonSceneSchedulerAbstract dungeonSceneScheduler;

    public DungeonSceneAbstract create(DungeonCfg dungeonCfg, SceneCfg sceneCfg) {
        DungeonSceneAbstract scene = new DungeonSceneAbstract();

        scene.setUid(IdUtil.generate());
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
