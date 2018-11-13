package com.wan37.logic.monster.init;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.logic.monster.config.MonsterInitAttrCfg;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MonsterCreator {

    @Autowired
    private IdTool idTool;

    @Autowired
    private MonsterInitializer monsterInitializer;

    public Monster create(MonsterCfg cfg) {
        Monster monster = new Monster();
        monster.setUid(idTool.generate());
        monster.setMonsterCfg(cfg);
        monster.setAttrs(cfg.getAttrs().stream()
                .collect(Collectors.toMap(MonsterInitAttrCfg::getId, MonsterInitAttrCfg::getValue)));

        monsterInitializer.init(monster);
        return monster;
    }
}
