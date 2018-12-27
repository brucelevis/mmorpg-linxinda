package com.wan37.logic.skill.cast.before;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.monster.Monster;
import org.springframework.stereotype.Service;

/**
 * 被作为技能目标施放前处理
 */
@Service
public class FightingUnitBeCastBeforeHandler {

    public void handler(FightingUnit caster, FightingUnit target) {
        if (target instanceof Monster) {
            Monster monster = (Monster) target;
            // 怪物标记打人的人
            monster.setLastAttackId(caster.getUid());
        }
    }
}
