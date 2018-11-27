package com.wan37.logic.monster.ai;

import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.ISkill;
import com.wan37.util.DateTimeUtils;
import com.wan37.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonsterAutoAttacker {

    public void attack(Monster monster, Player player) {
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        ISkill skill = randSkill(monster, now);
        if (skill == null) {
            return;
        }

        //TODO: 检查怪物状态

        //TODO: 检查玩家状态

        long baseAttackVal = monster.getBaseAttackVal();
        double skillAddition = skill.getDemageAddition();
        long demage = Math.round(baseAttackVal * skillAddition);

        PlayerDb playerDb = player.getPlayerDb();
        long defense = playerDb.getPlayerStrengthDb().getBaseDefenseVal();
        demage -= defense;

        long curHp = playerDb.getHp();
        if (curHp > demage) {
            playerDb.setHp(curHp - demage);

            String msg = String.format("%s用%s攻击了你，造成伤害%s", monster.getName(), skill.getName(), demage);
            player.syncClient(msg);
        } else {
            // 死了
        }

        //TODO: 触发技能Buff

        skill.setLastUseTime(now);
    }

    private ISkill randSkill(Monster monster, long now) {
        List<ISkill> skills = monster.getSkills().stream()
                .filter(i -> i.getLastUseTime() + i.getCdInterval() <= now)
                .collect(Collectors.toList());

        int size = skills.size();
        if (size == 0) {
            return null;
        }

        return skills.get(RandomUtil.rand(size));
    }
}
