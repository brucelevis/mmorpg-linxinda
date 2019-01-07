package com.wan37.logic.dungeon.schedule;

import com.wan37.logic.dungeon.complete.DungeonCompleteHandler;
import com.wan37.logic.dungeon.config.DungeonCfg;
import com.wan37.logic.dungeon.init.DungeonMonsterCreator;
import com.wan37.logic.dungeon.scene.DungeonScene;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.encode.MonsterEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 副本怪物刷新
 *
 * @author linda
 */
@Service
public class DungeonMonsterRefreshScheduler {

    @Autowired
    private DungeonMonsterCreator dungeonMonsterCreator;

    @Autowired
    private MonsterEncoder monsterEncoder;

    @Autowired
    private DungeonCompleteHandler dungeonCompleteHandler;

    public void schedule(DungeonScene scene) {
        if (!allMonstersDead(scene)) {
            return;
        }

        scene.getMonsters().clear();

        int nextMonsterGroup = scene.getMonsterGroupId() + 1;
        DungeonCfg dungeonCfg = scene.getDungeonCfg();
        if (nextMonsterGroup > dungeonCfg.getMonsters().size()) {
            // 副本通关
            dungeonCompleteHandler.handle(scene);
            return;
        }

        scene.setMonsterGroupId(nextMonsterGroup);
        scene.setMonsters(dungeonCfg.getMonsters().get(nextMonsterGroup).getMonsters().stream()
                .map(c -> dungeonMonsterCreator.create(c, scene))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        // 怪物推送
        String msg = "新的怪物出现了\n" + scene.getMonsters().stream()
                .map(m -> monsterEncoder.encode(m))
                .collect(Collectors.joining("\n"));
        scene.getPlayers().forEach(p -> p.syncClient(msg));
    }

    private boolean allMonstersDead(DungeonScene scene) {
        return scene.getMonsters().stream()
                .noneMatch(Monster::isAlive);
    }
}
