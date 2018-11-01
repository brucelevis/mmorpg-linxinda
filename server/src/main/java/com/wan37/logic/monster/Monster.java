package com.wan37.logic.monster;

import com.wan37.logic.monster.config.MonsterCfg;

public class Monster implements IMonster {

    private Long uid;

    private MonsterCfg monsterCfg;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public MonsterCfg getMonsterCfg() {
        return monsterCfg;
    }

    public void setMonsterCfg(MonsterCfg monsterCfg) {
        this.monsterCfg = monsterCfg;
    }

    @Override
    public Integer getCfgId() {
        return monsterCfg.getId();
    }

    @Override
    public String getName() {
        return monsterCfg.getName();
    }
}
