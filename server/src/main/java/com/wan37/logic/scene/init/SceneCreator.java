package com.wan37.logic.scene.init;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.config.MonsterCfgLoader;
import com.wan37.logic.monster.init.MonsterCreator;
import com.wan37.logic.npc.config.NpcCfgLoader;
import com.wan37.logic.npc.init.NpcCreator;
import com.wan37.logic.scene.scene.Scene;
import com.wan37.logic.scene.config.SceneCfg;
import com.wan37.logic.scene.config.SceneCfgLoader;
import com.wan37.logic.scene.config.SceneMonsterCfg;
import com.wan37.logic.scene.schedule.SceneScheduler;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .map(m -> createMonster(m, sceneCfg.getId()))
                .filter(Objects::nonNull)
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

    private Monster createMonster(SceneMonsterCfg cfg, Integer sceneId) {
        return monsterCfgLoader.load(cfg.getCfgId())
                .map(c -> monsterCreator.create(c, sceneId))
                .orElse(null);
    }
}
