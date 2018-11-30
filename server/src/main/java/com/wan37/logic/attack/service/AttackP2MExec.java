package com.wan37.logic.attack.service;

import com.wan37.exception.GeneralErrorExecption;
import com.wan37.logic.attack.fighting.FightingAttackHandler;
import com.wan37.logic.attack.fighting.after.FightingAfterHandler;
import com.wan37.logic.attack.fighting.before.FightingBeforeChecker;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.monster.encode.MonsterEncoder;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.scene.SceneActorSceneGetter;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AttackP2MExec {

    @Autowired
    private MonsterEncoder monsterEncoder;

    @Autowired
    private FightingBeforeChecker fightingBeforeChecker;

    @Autowired
    private FightingAfterHandler fightingAfterHandler;

    @Autowired
    private FightingAttackHandler fightingAttackHandler;

    @Autowired
    private SceneActorSceneGetter sceneActorSceneGetter;

    public void exec(Player player, Integer skillId, Long monsterUid) {
        AbstractScene scene = sceneActorSceneGetter.get(player);
        Monster monster = scene.getMonsters().stream()
                .filter(m -> Objects.equals(m.getUid(), monsterUid))
                .findAny()
                .orElseThrow(() -> new GeneralErrorExecption("目标怪物不存在"));

        ISkill skill = player.getSkills().get(skillId);
        // 攻击前检查
        if (!fightingBeforeChecker.check(player, monster, skill)) {
            return;
        }

        // 怪物标记打人的人
        monster.setLastAttackId(player.getUid());

        // 攻击
        fightingAttackHandler.handle(player, monster, skill, scene);

        // 攻击后
        fightingAfterHandler.handle(player, monster, skill, scene);

        // 通知场景玩家怪物状态更新
        String monsterUpdate = "怪物状态更新推送|" + monsterEncoder.encode(monster);
        scene.getPlayers().forEach(p -> p.syncClient(monsterUpdate));
    }
}
