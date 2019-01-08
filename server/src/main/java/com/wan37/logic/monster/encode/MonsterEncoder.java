package com.wan37.logic.monster.encode;

import com.wan37.logic.monster.Monster;
import org.springframework.stereotype.Service;

/**
 * 怪物信息编码
 *
 * @author linda
 */
@Service
public class MonsterEncoder {

    public String encode(Monster monster) {
        String msg = "名字：%s，Hp：%s，存活：%s（monsterUid：%s）";
        return String.format(msg, monster.getName(), monster.getHp(), monster.isAlive(), monster.getUid());
    }
}
