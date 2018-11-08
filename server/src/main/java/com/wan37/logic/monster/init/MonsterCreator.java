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

        //FIXME: 先写死血量和蓝量
        monster.setHp(100);
        monster.setMp(100);

        monster.setAlive(true);

        //FIXME: 先写死怪物生成cd（秒）
        monster.setCd(5);

        return monster;
    }
}
