package com.wan37.logic.scene.base;

import com.wan37.logic.buff.Buff;
import com.wan37.logic.skill.Skill;

import java.util.List;
import java.util.Map;

/**
 * 攻击单位（怪物，人，召唤兽）统一战斗接口实例
 *
 * @author linda
 */
public interface FightingUnit extends SceneActor {

    /**
     * 等级
     *
     * @return int
     */
    int getLevel();

    /**
     * 属性
     *
     * @return Map
     */
    Map<Integer, Double> getAttrs();

    /**
     * 技能
     *
     * @return Map
     */
    Map<Integer, Skill> getSkills();

    /**
     * 挂在身上的Buff
     *
     * @return List<Buff>
     */
    List<Buff> getBuffs();

    /**
     * 总的基础攻击战力值
     *
     * @return long
     */
    long getBaseAttackVal();

    /**
     * 总的基础防御值
     *
     * @return long
     */
    long getBaseDefenseVal();
}
