package com.wan37.logic.dungeon.init;

import com.wan37.logic.dungeon.config.DungeonMonsterCfg;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterCfgLoader;
import com.wan37.logic.monster.init.MonsterCreator;
import com.wan37.logic.scene.base.AbstractScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 副本怪物生成类
 */
@Service
public class DungeonMonsterCreator {

    @Autowired
    private MonsterCfgLoader monsterCfgLoader;

    @Autowired
    private MonsterCreator monsterCreator;

    public List<Monster> create(DungeonMonsterCfg cfg, AbstractScene scene) {
        MonsterCfg monsterCfg = monsterCfgLoader.load(cfg.getMonsterId()).orElse(null);
        if (monsterCfg == null) {
            return null;
        }

        return IntStream.range(0, cfg.getAmount())
                .mapToObj(i -> monsterCreator.create(monsterCfg, scene))
                .collect(Collectors.toList());
    }
}
