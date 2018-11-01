package com.wan37.logic.monster.init;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.config.MonsterCfg;
import com.wan37.util.IdTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonsterCreator {

    @Autowired
    private IdTool idTool;

    public Monster create(MonsterCfg cfg) {
        Monster monster = new Monster();
        monster.setUid(idTool.generate());
        monster.setMonsterCfg(cfg);
        return monster;
    }
}
