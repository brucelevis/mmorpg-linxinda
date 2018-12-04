package com.wan37.logic.attack.fighting.after;

import com.wan37.logic.attack.fighting.FightingUnit;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.buff.rand.SkillBuffRandomer;
import com.wan37.logic.equipment.EquipPartEnum;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.scene.base.AbstractScene;
import com.wan37.logic.skill.ISkill;
import com.wan37.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FightingAfterHandler {

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    @Autowired
    private SkillBuffRandomer skillBuffRandomer;

    public void handle(FightingUnit attacker, FightingUnit target, ISkill skill, AbstractScene scene) {
        // 扣蓝
        long mp = attacker.getMp() - skill.getCostMp();
        attacker.setMp(mp < 0 ? 0 : mp);

        if (isPlayer(attacker)) {
            Player player = (Player) attacker;

            PlayerDb playerDb = player.getPlayerDb();
            EquipDb equipDb = playerDb.getEquipDb();
            ItemDb equipItem = equipDb.getItems().get(EquipPartEnum.PART_1.getId());
            if (equipItem != null) {
                //FIXME: 写死减少装备耐久度1
                EquipExtraDb equipExtraDb = equipExtraDbGetter.get(equipItem.getExtraDb());
                equipExtraDb.setDurabilityv(equipExtraDb.getDurabilityv() - 1);
            }
        }

        // 设置技能cd
        long now = DateTimeUtils.toEpochMilli(LocalDateTime.now());
        skill.setLastUseTime(now);

        // 攻击技能概率触发Buff
        skill.getSkillCfg().getBuffs().forEach(c -> skillBuffRandomer.rand(attacker, target, c, scene));

        //TODO: 怪物触发被动
    }

    private boolean isPlayer(FightingUnit unit) {
        return unit instanceof Player;
    }
}
