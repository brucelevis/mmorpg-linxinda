package com.wan37.logic.scene.init;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterCfgLoader;
import com.wan37.logic.monster.init.MonsterCreator;
import com.wan37.logic.npc.config.NpcCfgLoader;
import com.wan37.logic.npc.init.NpcCreator;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.config.SceneMonsterCfg;
import com.wan37.logic.scene.scene.Scene;
import com.wan37.logic.scene.schedule.SceneScheduler;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SceneCreator {

    @Autowired
    private SceneCfgLoader sceneCfgLoader;

    @Autowired
    private MonsterCreator monsterCreator;

    @Autowired
    private MonsterCfgLoader monsterCfgLoader;

    @Autowired
    private NpcCreator npcCreator;

    @Autowired
    private NpcCfgLoader npcCfgLoader;

    @Autowired
    private SceneScheduler sceneScheduler;

    public Scene create(Integer sceneId) {
        SceneCfg sceneCfg = sceneCfgLoader.load(sceneId)
                .orElseThrow(() -> new RuntimeException("找不到SceneCfg"));

        Scene scene = new Scene();
        scene.setSceneCfg(sceneCfg);
        scene.setPlayers(new ArrayList<>());

        scene.setMonsters(sceneCfg.getMonsters().stream()
                .map(m -> createMonsters(m, scene))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        scene.setNpcs(sceneCfg.getNpcs().stream()
                .map(i -> npcCfgLoader.load(i))
                .filter(Optional::isPresent)
                .map(c -> npcCreator.create(c.get()))
                .collect(Collectors.toList()));

        scene.setSceneScheduler(sceneScheduler);
        scene.setLastRecoverMpTime(DateTimeUtils.toEpochMilli(LocalDateTime.now()));

        return scene;
    }

    private List<Monster> createMonsters(SceneMonsterCfg cfg, AbstractScene scene) {
        MonsterCfg monsterCfg = monsterCfgLoader.load(cfg.getCfgId()).orElse(null);
        if (monsterCfg == null) {
            return null;
        }

        return IntStream.range(0, cfg.getAmount())
                .mapToObj(i -> monsterCreator.create(monsterCfg, scene))
                .collect(Collectors.toList());
    }
}
