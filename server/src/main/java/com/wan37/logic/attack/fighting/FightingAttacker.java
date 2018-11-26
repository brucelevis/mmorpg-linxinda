package com.wan37.logic.attack.fighting;

import org.springframework.stereotype.Service;

@Service
public class FightingAttacker {

    public void attack(FightingUnit from, Integer skillId, FightingUnit target) {
        FightingSkill attackSkill = from.getSkills().get(skillId);
        if (attackSkill == null) {

        }
    }
}
