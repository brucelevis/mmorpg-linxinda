package com.wan37.logic.monster.ai;

import com.wan37.logic.attack.fighting.FightingAttackHandler;
import com.wan37.logic.attack.fighting.after.FightingAfterHandler;
import com.wan37.logic.attack.fighting.before.FightingBeforeChecker;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.scene.Scene;
import com.wan37.logic.skill.ISkill;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public void attack(Monster monster, Player player, Scene scene) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        ISkill skill = randSkill(monster, now);
        if (skill == null) {
            // 技能cd
            return;
        }

        // 攻击前检查
        if (!fightingBeforeChecker.check(monster, player, skill)) {
            return;
        }

        // 攻击
        fightingAttackHandler.handle(monster, player, skill);

        // 攻击后
        fightingAfterHandler.handle(monster, player, skill);

        String msg = "玩家状态更新推送|" + playerInfoEncoder.encode(player);
        scene.getPlayers().forEach(p -> p.syncClient(msg));
    }

    private ISkill randSkill(Monster monster, long now) {
        List<ISkill> skills = monster.getSkills().values().stream()
                .filter(i -> i.getLastUseTime() + i.getCdInterval() <= now)
                .collect(Collectors.toList());

        int size = skills.size();
        if (size == 0) {
            return null;
        }

        return skills.get(RandomUtil.rand(size));
    }
}
