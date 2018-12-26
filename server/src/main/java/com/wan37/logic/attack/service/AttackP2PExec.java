package com.wan37.logic.attack.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.attack.fighting.FightingAttackHandler;
import com.wan37.logic.attack.fighting.after.FightingAfterHandler;
import com.wan37.logic.attack.fighting.before.FightingBeforeChecker;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.player.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttackP2PExec {

    @Autowired
    private FightingBeforeChecker fightingBeforeChecker;

    @Autowired
    private FightingAttackHandler fightingAttackHandler;

    @Autowired
    private FightingAfterHandler fightingAfterHandler;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    public void exec(Player attacker, Player target, Integer skillId) {
        AbstractScene scene = sceneActorSceneGetter.get(attacker);
        if (!scene.canAttack()) {
            throw new GeneralErrorExecption("该地图不允许攻击");
        }

        //TODO: 竞技场才能攻击玩家

        ISkill skill = attacker.getSkills().get(skillId);
        // 攻击前检查
        if (!fightingBeforeChecker.check(attacker, target, skill)) {
            return;
        }

        // 攻击
        fightingAttackHandler.handle(attacker, target, skill, scene);

        // 攻击后
        fightingAfterHandler.handle(attacker, target, skill, scene);

        String msg1 = "玩家状态更新推送|" + playerInfoEncoder.encode(attacker);
        scene.getPlayers().forEach(p -> p.syncClient(msg1));

        String msg2 = "玩家状态更新推送|" + playerInfoEncoder.encode(target);
        scene.getPlayers().forEach(p -> p.syncClient(msg2));
    }
}
