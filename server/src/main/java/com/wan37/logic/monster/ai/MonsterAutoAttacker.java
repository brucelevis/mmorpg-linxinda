package com.wan37.logic.monster.ai;

import com.wan37.logic.attack.fighting.FightingAttackHandler;
import com.wan37.logic.attack.fighting.after.FightingAfterHandler;
import com.wan37.logic.attack.fighting.before.FightingBeforeChecker;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.ISkill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonsterAutoAttacker {

    @Autowired
    private FightingBeforeChecker fightingBeforeChecker;

    @Autowired
    private FightingAttackHandler fightingAttackHandler;

    @Autowired
    private FightingAfterHandler fightingAfterHandler;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    public void attack(Monster monster, Player player, ISkill skill, AbstractScene scene) {
        // 攻击前检查
        if (!fightingBeforeChecker.check(monster, player, skill)) {
            return;
        }

        // 攻击
        fightingAttackHandler.handle(monster, player, skill, scene);

        // 攻击后
        fightingAfterHandler.handle(monster, player, skill, scene);

        notifyPlayer(player, scene);
    }

    public void attack(Monster monster, List<Player> players, ISkill skill, AbstractScene scene) {
        // 攻击
        players.forEach(p -> fightingAttackHandler.handle(monster, p, skill, scene));

        // 设置技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        skill.setLastUseTime(now);

        players.forEach(p -> notifyPlayer(p, scene));
    }

    private void notifyPlayer(Player player, AbstractScene scene) {
        String msg = "玩家状态更新推送|" + playerInfoEncoder.encode(player);
        scene.getPlayers().forEach(p -> p.syncClient(msg));
    }
}
