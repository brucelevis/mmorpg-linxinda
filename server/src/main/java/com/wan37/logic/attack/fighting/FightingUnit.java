package com.wan37.logic.attack.fighting;

import com.wan37.logic.buff.IBuff;
import com.wan37.logic.scene.base.SceneActor;
import com.wan37.logic.skill.ISkill;

import java.util.List;
import java.util.Map;

public interface FightingUnit extends SceneActor {

    int getLevel();

    Map<Integer, Double> getAttrs();

    Map<Integer, ISkill> getSkills();

    List<IBuff> getBuffs();

    long getBaseAttackVal();

    long getBaseDefenseVal();
}
