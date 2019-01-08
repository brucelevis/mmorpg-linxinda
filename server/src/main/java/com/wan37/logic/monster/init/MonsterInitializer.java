package com.wan37.logic.monster.init;

import com.wan37.logic.monster.Monster;
import org.springframework.stereotype.Service;

/**
 * 怪物初始化
 *
 * @author linda
 */
@Service
public class MonsterInitializer {

    public void init(Monster monster) {
        monster.setDeadTime(0);
        monster.setAlive(true);
        monster.setHp(monster.getMaxHp());
        monster.setMp(monster.getMaxMp());
    }
}
