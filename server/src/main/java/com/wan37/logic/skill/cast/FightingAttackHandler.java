package com.wan37.logic.skill.cast;

import com.wan37.event.entity.DieEvent;
import com.wan37.event.GeneralEventListenersManager;
import com.wan37.logic.scene.base.FightingUnit;
import com.wan37.logic.buff.BuffEffectEnum;
import com.wan37.logic.buff.entity.Buff;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.entity.Skill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 技能攻击处理
 *
 * @author linda
 */
@Service
public class FightingAttackHandler {

    @Autowired
    private GeneralEventListenersManager generalEventListenersManager;

    public void handle(FightingUnit attacker, FightingUnit target, Skill skill, AbstractScene scene) {
        double baseAttackVal = attacker.getBaseAttackVal();
        double skillDamageAddition = skill.getEffectValue();
        long damage = Math.round(baseAttackVal * skillDamageAddition);

        long defense = target.getBaseDefenseVal();
        damage -= defense;

        if (damage <= 0) {
            String notify = String.format("[%s]用[%s]攻击[%s]，造成伤害%s", attacker.getName(), skill.getName(), target.getName(), 0);
            scene.getPlayers().forEach(p -> p.syncClient(notify));
            return;
        }

        // 护盾抵挡
        List<Buff> shieldBuffs = target.getBuffs().stream()
                .filter(b -> Objects.equals(b.getId(), BuffEffectEnum.SHIELD.getId()))
                .collect(Collectors.toList());
        for (Buff buff : shieldBuffs) {
            long shield = Long.parseLong(buff.getArg());
            if (damage >= shield) {
                // 护盾打破
                String notify = String.format("[%s]用[%s]抵挡了来自%s的%s点伤害", target.getName(), buff.getName(), attacker.getName(), shield);
                scene.getPlayers().forEach(p -> p.syncClient(notify));

                damage -= shield;
                target.getBuffs().remove(buff);
            } else {
                String notify = String.format("[%s]用[%s]抵挡了来自%s的%s点伤害", target.getName(), buff.getName(), attacker.getName(), damage);
                scene.getPlayers().forEach(p -> p.syncClient(notify));

                damage = 0;
                target.getBuffs().remove(buff);
            }

            if (damage <= 0) {
                break;
            }
        }

        long curHp = target.getHp();
        if (curHp > damage) {
            // 没死
            target.setHp(curHp - damage);

            String notify = String.format("[%s]用[%s]攻击[%s]，造成伤害%s", attacker.getName(), skill.getName(), target.getName(), damage);
            scene.getPlayers().forEach(p -> p.syncClient(notify));
        } else {
            // 死了
            target.setHp(0);

            String notify = String.format("[%s]用[%s]击杀了[%s]，造成伤害%s", attacker.getName(), skill.getName(), target.getName(), damage);
            scene.getPlayers().forEach(p -> p.syncClient(notify));

            long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
            generalEventListenersManager.fireEvent(new DieEvent(target, now));
        }
    }
}
