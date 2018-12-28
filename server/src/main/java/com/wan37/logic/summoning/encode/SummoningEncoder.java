package com.wan37.logic.summoning.encode;

import com.wan37.logic.summoning.entity.Summoning;
import org.springframework.stereotype.Service;

@Service
public class SummoningEncoder {

    public String encode(Summoning summoning) {
        String msg = "名字：%s，Hp：%s（monsterUid：%s）";
        return String.format(msg, summoning.getName(), summoning.getHp(), summoning.getUid());
    }
}
