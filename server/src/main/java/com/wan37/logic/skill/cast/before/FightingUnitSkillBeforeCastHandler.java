package com.wan37.logic.skill.cast.before;

import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.player.Player;
import com.wan37.logic.skill.Skill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 技能施放前逻辑处理
 *
 * @author linda
 */
@Service
public class FightingUnitSkillBeforeCastHandler {

    @Autowired
    private PlayerSkillBeforeCastHandler playerSkillBeforeCastHandler;

    public void handle(FightingUnit caster, Skill skill) {
        if (isPlayer(caster)) {
            Player player = (Player) caster;
            playerSkillBeforeCastHandler.handler(player, skill);
        }

        // 设置技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        skill.setLastUseTime(now);
    }

    private boolean isPlayer(FightingUnit unit) {
        return unit instanceof Player;
    }
}
