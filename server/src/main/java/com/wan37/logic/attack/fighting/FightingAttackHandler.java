package com.wan37.logic.attack.fighting;

import com.wan37.event.DieEvent;
import com.wan37.event.GenernalEventListenersManager;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.scene.SceneGlobalManager;
import com.wan37.logic.skill.ISkill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FightingAttackHandler {

    @Autowired
    private SceneGlobalManager sceneGlobalManager;

    @Autowired
    private GenernalEventListenersManager genernalEventListenersManager;

    public void handle(FightingUnit attacker, FightingUnit target, ISkill skill) {
        Scene scene = sceneGlobalManager.getScene(attacker.getSceneId());

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

        //TODO: 怪物护盾抵挡

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
