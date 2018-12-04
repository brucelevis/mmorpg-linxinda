package com.wan37.logic.attack.fighting;

import com.wan37.event.DieEvent;
import com.wan37.event.GenernalEventListenersManager;
import com.wan37.logic.buff.BuffEffectEnum;
import com.wan37.logic.buff.IBuff;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.ISkill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FightingAttackHandler {

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public void handle(FightingUnit attacker, FightingUnit target, ISkill skill, AbstractScene scene) {
        double baseAttackVal = attacker.getBaseAttackVal();
        double skillDemageAddition = skill.getDemageAddition();
        long demage = Math.round(baseAttackVal * skillDemageAddition);

        long defense = target.getBaseDefenseVal();
        demage -= defense;

        if (demage <= 0) {
            String notify = String.format("[%s]用[%s]攻击[%s]，造成伤害%s", attacker.getName(), skill.getName(), target.getName(), 0);
            scene.getPlayers().forEach(p -> p.syncClient(notify));
            return;
        }

        // 护盾抵挡
        List<IBuff> shieldBuffs = target.getBuffs().stream()
                .filter(b -> Objects.equals(b.getId(), BuffEffectEnum.BUFF_EFFECT_3.getId()))
                .collect(Collectors.toList());
        for (IBuff buff : shieldBuffs) {
            long shield = Long.parseLong(buff.getArg());
            if (demage >= shield) {
                // 护盾打破
                String notify = String.format("[%s]用[%s]抵挡了来自%s的%s点伤害", target.getName(), buff.getName(), attacker.getName(), shield);
                scene.getPlayers().forEach(p -> p.syncClient(notify));

                demage -= shield;
                target.getBuffs().remove(buff);
            } else {
                String notify = String.format("[%s]用[%s]抵挡了来自%s的%s点伤害", target.getName(), buff.getName(), attacker.getName(), demage);
                scene.getPlayers().forEach(p -> p.syncClient(notify));

                demage = 0;
                target.getBuffs().remove(buff);
            }

            if (demage <= 0) {
                break;
            }
        }

        long curHp = target.getHp();
        if (curHp > demage) {
            // 没死
            target.setHp(curHp - demage);

            String notify = String.format("[%s]用[%s]攻击[%s]，造成伤害%s", attacker.getName(), skill.getName(), target.getName(), demage);
            scene.getPlayers().forEach(p -> p.syncClient(notify));
        } else {
            // 死了
            target.setHp(0);

            String notify = String.format("[%s]用[%s]击杀了[%s]，造成伤害%s", attacker.getName(), skill.getName(), target.getName(), demage);
            scene.getPlayers().forEach(p -> p.syncClient(notify));

            long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
            genernalEventListenersManager.fireEvent(new DieEvent(target, now));
        }
    }
}
