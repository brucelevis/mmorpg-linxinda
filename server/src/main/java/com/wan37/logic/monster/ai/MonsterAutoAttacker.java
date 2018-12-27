package com.wan37.logic.monster.ai;

import com.wan37.logic.skill.cast.FightingAttackHandler;
import com.wan37.logic.skill.cast.after.FightingAfterHandler;
import com.wan37.logic.skill.cast.check.FightingUnitSkillBeforeCastChecker;
import com.wan37.logic.buff.rand.SkillBuffRandomer;
import com.wan37.logic.monster.Monster;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.encode.PlayerInfoEncoder;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.entity.ISkill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonsterAutoAttacker {

    @Autowired
    private FightingUnitSkillBeforeCastChecker fightingUnitSkillBeforeCastChecker;

    @Autowired
    private FightingAttackHandler fightingAttackHandler;

    @Autowired
    private FightingAfterHandler fightingAfterHandler;

    @Autowired
    private PlayerInfoEncoder playerInfoEncoder;

    @Autowired
    private SkillBuffRandomer skillBuffRandomer;

    public void attack(Monster monster, Player player, ISkill skill, AbstractScene scene) {
//        // 攻击前检查
//        if (!fightingUnitSkillBeforeCastChecker.check(monster, player, skill)) {
//            return;
//        }
//
//        // 攻击
//        fightingAttackHandler.handle(monster, player, skill, scene);
//
//        // 攻击后
//        fightingAfterHandler.handle(monster, player, skill, scene);
//
//        notifyPlayer(player, scene);
    }

    public void attack(Monster monster, List<Player> players, ISkill skill, AbstractScene scene) {
        // 扣蓝
        long mp = monster.getMp() - skill.getCostMp();
        monster.setMp(mp < 0 ? 0 : mp);

        // 攻击
        players.forEach(p -> fightingAttackHandler.handle(monster, p, skill, scene));

        // 设置技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        skill.setLastUseTime(now);

        // 攻击技能概率触发Buff
        skill.getSkillCfg().getBuffs()
                .forEach(c -> players.forEach(p -> skillBuffRandomer.rand(monster, p, c, scene)));

        players.forEach(p -> notifyPlayer(p, scene));
    }

    private void notifyPlayer(Player player, AbstractScene scene) {
        String msg = "玩家状态更新推送|" + playerInfoEncoder.encode(player);
        scene.getPlayers().forEach(p -> p.syncClient(msg));
    }
}
