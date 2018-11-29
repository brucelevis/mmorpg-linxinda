package com.wan37.logic.dungeon.init;

import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.config.DungeonMonsterCfg;
import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterCfgLoader;
import com.wan37.logic.monster.init.MonsterCreator;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DungeonSceneCreator {

    @Autowired
    private IdTool idTool;

    @Autowired
    private MonsterCreator monsterCreator;

    @Autowired
    private MonsterCfgLoader monsterCfgLoader;

    public DungeonScene create(DungeonCfg dungeonCfg, SceneCfg sceneCfg) {
        DungeonScene scene = new DungeonScene();

        scene.setUid(idTool.generate());
        scene.setSceneCfg(sceneCfg);
        scene.setPlayers(new ArrayList<>());
        scene.setNpcs(new ArrayList<>());

        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        scene.setExpireTime(now + TimeUnit.MINUTES.toMillis(dungeonCfg.getLimitTime()));

        // 开始怪物组的id为1
        scene.setMonsterGroupId(1);
        scene.setMonsters(dungeonCfg.getMonsters().get(1).getMonsters().stream()
                .map(c -> createMonsters(c, sceneCfg.getId()))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        return scene;
    }

    private List<Monster> createMonsters(DungeonMonsterCfg cfg, Integer sceneId) {
        MonsterCfg monsterCfg = monsterCfgLoader.load(cfg.getMonsterId()).orElse(null);
        if (monsterCfg == null) {
            return null;
        }

        return IntStream.range(0, cfg.getAmount())
                .mapToObj(i -> monsterCreator.create(monsterCfg, sceneId))
                .collect(Collectors.toList());
    }
}
