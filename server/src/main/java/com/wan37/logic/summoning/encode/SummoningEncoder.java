package com.wan37.logic.summoning.encode;

import com.wan37.logic.summoning.Summoning;
import org.springframework.stereotype.Service;

/**
 * 召唤兽信息编码
 *
 * @author linda
 */
@Service
public class SummoningEncoder {

    public String encode(Summoning summoning) {
        String msg = "名字：%s，Hp：%s（monsterUid：%s）";
        return String.format(msg, summoning.getName(), summoning.getHp(), summoning.getUid());
    }
}
