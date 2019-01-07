package com.wan37.logic.skill.cast.before;

import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.EquipPartEnum;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.database.PlayerDb;
import com.wan37.logic.skill.entity.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人施放技能前独有的逻辑处理
 */
@Service
public class PlayerSkillBeforeCastHandler {

    @Autowired
    private EquipExtraDbGetter equipExtraDbGetter;

    public void handler(Player player, ISkill skill) {
        // 扣蓝
        long mp = player.getMp() - skill.getCostMp();
        player.setMp(mp < 0 ? 0 : mp);

        PlayerDb playerDb = player.getPlayerDb();
        EquipDb equipDb = playerDb.getEquipDb();
        ItemDb equipItem = equipDb.getItems().get(EquipPartEnum.PART_1.getId());
        if (equipItem != null) {
            //FIXME: 写死减少装备耐久度1
            EquipExtraDb equipExtraDb = equipExtraDbGetter.get(equipItem.getExtraDb());
            equipExtraDb.setDurabilityv(equipExtraDb.getDurabilityv() - 1);
        }
    }
}