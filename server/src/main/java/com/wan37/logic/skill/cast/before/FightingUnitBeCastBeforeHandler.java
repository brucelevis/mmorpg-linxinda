package com.wan37.logic.skill.cast.before;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.monster.Monster;
import org.springframework.stereotype.Service;

/**
 * 被作为技能目标的对象施放前处理
 *
 * @author linda
 */
@Service
public class FightingUnitBeCastBeforeHandler {

    public void handle(FightingUnit caster, FightingUnit target) {
        if (target instanceof Monster) {
            Monster monster = (Monster) target;
            // 怪物标记打人的人
            monster.setLastAttackId(caster.getUid());
        }
    }
}
